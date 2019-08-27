package dev.olog.basil.data.remote.mapper

import dev.olog.basil.core.Allergens
import dev.olog.basil.core.Recipe
import dev.olog.basil.core.RecipeCategory
import dev.olog.basil.data.remote.model.SpoonacularRecipe

private const val MAX_IMAGE_SIZE = "636x393"

// https://spoonacular.com/food-api/docs#Show-Images
internal fun SpoonacularRecipe.toDomain(): Recipe {
    return Recipe(
        id = this.id,
        name = this.title,
        description = "Description",
        allergens = Allergens(
            dairyFree = this.dairyFree,
            glutenFree = this.glutenFree,
            ketogenic = this.ketogenic,
            vegan = this.vegan,
            vegetarian = this.vegetarian
        ),
        category = RecipeCategory.Entree,
        ingredients = listOf(),
        image = this.image
    )
}