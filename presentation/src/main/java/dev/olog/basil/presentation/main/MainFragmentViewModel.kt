package dev.olog.basil.presentation.main

import dev.olog.basil.core.Ingredient
import dev.olog.basil.core.Recipe
import dev.olog.basil.core.RecipeCategory
import dev.olog.basil.presentation.RecipeFactory
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class MainFragmentViewModel {

    private val recipesPublisher = ConflatedBroadcastChannel(RecipeFactory.get())
    private val currentRecipeIndexPublisher = ConflatedBroadcastChannel(0)

    private val currentRecipeCategoryPublisher = ConflatedBroadcastChannel(RecipeCategory.Entree)

    fun updateVisibleCategory(category: RecipeCategory) {
        currentRecipeCategoryPublisher.offer(category)
    }

    fun observeCurrentRecipeCategory(): Flow<RecipeCategory> = currentRecipeCategoryPublisher.asFlow()

    fun updatePosition(position: Int) {
        currentRecipeIndexPublisher.offer(position)
    }

    fun observeRecipes(): Flow<List<Recipe>> {
        return currentRecipeCategoryPublisher.asFlow()
                .flatMapLatest { category ->
                    recipesPublisher.asFlow()
                            .map { recipes -> recipes.filter { it.category == category } }
                }
    }

    fun observeCurrentRecipe(): Flow<Recipe> {
        return recipesPublisher.asFlow()
                .map { recipes -> recipes[currentRecipeIndexPublisher.value] }
    }

    fun observeCurrentIngredients(): Flow<List<Ingredient>> {
        return observeCurrentRecipe().map { it.ingredients }
    }

}
