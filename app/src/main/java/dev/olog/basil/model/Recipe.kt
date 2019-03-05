package dev.olog.basil.model

data class Recipe(
        val id: Long,
        val name: String,
        val description: String,
        val macros: Macros,
        val allergens: Allergens,
        val category: RecipeCategory,
        val ingredients: List<Ingredient>,
        val image: Int
)

data class Ingredient(
        val name: String,
        val quantity: Int,
        val order: Int
)

data class Macros(
        val calories: Int,
        val carbohydrate: Int,
        val proteins: Int,
        val fat: Int
)

data class Allergens(
        val glutenFree: Boolean,
        val eggFree: Boolean
)

enum class RecipeCategory {
    Appetizer,
    Entree,
    Dessert,
    Cocktail
}