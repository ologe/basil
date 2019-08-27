package dev.olog.basil.core

data class Recipe(
    val id: Int,
    val name: String,
    val description: String,
    val allergens: Allergens,
    val category: RecipeCategory,
    val ingredients: List<Ingredient>,
    val image: String
)

data class Ingredient(
    val name: String,
    val quantity: Int,
    val order: Int
)

data class Allergens(
    val dairyFree: Boolean,
    val glutenFree: Boolean,
    val ketogenic: Boolean,
    val vegan: Boolean,
    val vegetarian: Boolean
)

enum class RecipeCategory {
    Appetizer,
    Entree,
    Dessert,
    Cocktail
}