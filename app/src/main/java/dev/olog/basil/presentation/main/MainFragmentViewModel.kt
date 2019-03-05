package dev.olog.basil.presentation.main

import dev.olog.basil.model.Ingredient
import dev.olog.basil.model.Recipe
import dev.olog.basil.model.RecipeCategory
import dev.olog.basil.utils.RecipeFactory
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor

class MainFragmentViewModel {

    private val recipesPublisher = BehaviorProcessor.create<List<Recipe>>()
    private val currentRecipeIndexPublisher = BehaviorProcessor.createDefault(0)

    private val currentRecipeCategoryPublisher = BehaviorProcessor.createDefault(RecipeCategory.Entree)

    init {
        recipesPublisher.onNext(RecipeFactory.get())
    }

    fun updateVisibleCategory(category: RecipeCategory){
        currentRecipeCategoryPublisher.onNext(category)
    }

    fun observeCurrentRecipeCategory() : Flowable<RecipeCategory> = currentRecipeCategoryPublisher

    fun updatePosition(position: Int) {
        currentRecipeIndexPublisher.onNext(position)
    }

    fun observeRecipes(): Flowable<List<Recipe>> {
        return currentRecipeCategoryPublisher.flatMap { category ->
            recipesPublisher.map { recipes -> recipes.filter { it.category == category } }
        }
    }

    fun observeCurrentRecipe(): Flowable<Recipe> {
        return recipesPublisher.map { recipes -> recipes[currentRecipeIndexPublisher.value!!] }
    }

    fun observeCurrentIngredients(): Flowable<List<Ingredient>> {
        return observeCurrentRecipe().map { it.ingredients }
    }

}
