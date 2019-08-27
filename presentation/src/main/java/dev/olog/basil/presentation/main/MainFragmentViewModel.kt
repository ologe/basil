package dev.olog.basil.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.olog.basil.core.Ingredient
import dev.olog.basil.core.Recipe
import dev.olog.basil.core.RecipeCategory
import dev.olog.basil.core.RecipeGateway
import dev.olog.basil.presentation.utils.map
import dev.olog.basil.presentation.utils.switchMap
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(
    private val gateway: RecipeGateway
) : ViewModel() {

    private val recipesLiveData = MutableLiveData<List<Recipe>>()

    private val currentRecipeIndexPublisher = ConflatedBroadcastChannel(0)

    private val currentRecipeCategoryPublisher = MutableLiveData(RecipeCategory.Entree)

    init {
        viewModelScope.launch {
            val data = gateway.getAll()
            recipesLiveData.value = data
        }
    }

    fun updateVisibleCategory(category: RecipeCategory) {
        currentRecipeCategoryPublisher.value = category
    }

    fun observeCurrentRecipeCategory(): LiveData<RecipeCategory> = currentRecipeCategoryPublisher

    fun updatePosition(position: Int) {
        currentRecipeIndexPublisher.offer(position)
    }

    fun observeRecipes(): LiveData<List<Recipe>> {
        return currentRecipeCategoryPublisher.switchMap { category ->
            recipesLiveData.map { it.filter { it.category == category } }
        }
    }

    fun observeCurrentRecipe(): LiveData<Recipe?> {
        return recipesLiveData.map {
            it.getOrNull(currentRecipeIndexPublisher.value)
        }
    }

    fun observeCurrentIngredients(): LiveData<List<Ingredient>> {
        return observeCurrentRecipe().map { it?.ingredients ?: emptyList() }
    }

}
