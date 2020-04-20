package dev.olog.basil.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.olog.basil.core.Ingredient
import dev.olog.basil.core.Recipe
import dev.olog.basil.core.RecipeCategory
import dev.olog.basil.core.RecipeGateway
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipesViewModel @Inject constructor(
    private val gateway: RecipeGateway
) : ViewModel() {

    private val recipesPublisher = ConflatedBroadcastChannel<List<Recipe>>()
    private var currentPositionPublisher = ConflatedBroadcastChannel(0)

    private val categoryPublisher = ConflatedBroadcastChannel(RecipeCategory.Entree)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val data = gateway.getAll()
            recipesPublisher.offer(data)
        }
    }

    override fun onCleared() {
        recipesPublisher.close()
        categoryPublisher.close()
        currentPositionPublisher.close()
    }

    fun updateVisibleCategory(category: RecipeCategory) {
        categoryPublisher.offer(category)
    }

    fun observeCurrentRecipeCategory(): Flow<RecipeCategory> = categoryPublisher.asFlow()

    fun updatePosition(position: Int) {
        currentPositionPublisher.offer(position)
    }

    fun observeRecipes(): Flow<List<Recipe>> {
        return categoryPublisher.asFlow().combine(recipesPublisher.asFlow()) { category, list ->
            list.filter { it.category == category }
        }
    }

    fun observeCurrentRecipe(): Flow<Recipe?> {
        return observeRecipes().combine(currentPositionPublisher.asFlow()) { list, position ->
            list.getOrNull(position)
        }
    }

    fun observeCurrentIngredients(): Flow<List<Ingredient>> {
        return observeCurrentRecipe().map { it?.ingredients ?: emptyList() }
    }

}
