package dev.olog.basil.utils

import dev.olog.basil.R
import dev.olog.basil.model.*
import kotlin.random.Random

object RecipeFactory {

    fun get(): List<Recipe> {
        var id = 0L
        val apetizers = listOf(
                Recipe(++id, "Creamy Pesto Pasta", loremImpus, randomMacros(), randomAllerges(), RecipeCategory.Appetizer, randomIngredients(), R.drawable.salmon),
                Recipe(++id, "Herb Roasted Chicken", loremImpus, randomMacros(), randomAllerges(), RecipeCategory.Appetizer, randomIngredients(), R.drawable.salmon),
                Recipe(++id, "Spinach Filo Puffs", loremImpus, randomMacros(), randomAllerges(), RecipeCategory.Appetizer, randomIngredients(), R.drawable.salmon)
        )

        val entrees = listOf(
                Recipe(++id, "Candied Tomatoes on Basil Leaves", loremImpus, randomMacros(), randomAllerges(), RecipeCategory.Entree, randomIngredients(), R.drawable.salmon),
                Recipe(++id, "Rockmelon Bruschetta with Goat's Ceese and Prosciutto", loremImpus, randomMacros(), randomAllerges(), RecipeCategory.Entree, randomIngredients(), R.drawable.salmon),
                Recipe(++id, "Goat's Cheese Pissaladiere Tarts", loremImpus, randomMacros(), randomAllerges(), RecipeCategory.Entree, randomIngredients(), R.drawable.salmon)
        )

        val desserts = listOf(
                Recipe(++id, "Pecan Caramel Candies", loremImpus, randomMacros(), randomAllerges(), RecipeCategory.Dessert, randomIngredients(), R.drawable.salmon),
                Recipe(++id, "No-Bake Chocolate Hazelnut Thumbprints", loremImpus, randomMacros(), randomAllerges(), RecipeCategory.Dessert, randomIngredients(), R.drawable.salmon),
                Recipe(++id, "Cherry Divinity", loremImpus, randomMacros(), randomAllerges(), RecipeCategory.Dessert, randomIngredients(), R.drawable.salmon)
        )

        val cocktails = listOf(
                Recipe(++id, "Negroni", loremImpus, randomMacros(), randomAllerges(), RecipeCategory.Cocktail, randomIngredients(), R.drawable.salmon),
                Recipe(++id, "Jin Tonic", loremImpus, randomMacros(), randomAllerges(), RecipeCategory.Cocktail, randomIngredients(), R.drawable.salmon),
                Recipe(++id, "Malibu", loremImpus, randomMacros(), randomAllerges(), RecipeCategory.Cocktail, randomIngredients(), R.drawable.salmon)
        )

        return apetizers.plus(entrees).plus(desserts).plus(cocktails)
    }

    private val loremImpus = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam."

    private fun randomMacros(): Macros {
        val carbos = Random.nextInt(10, 100)
        val proteins = Random.nextInt(5, 20)
        val fat = Random.nextInt(5, 20)
        return Macros(
                carbos * 4 + proteins * 4 + fat * 9,
                carbos, proteins, fat
        )
    }

    private fun randomAllerges(): Allergens {
        return Allergens(Random.nextBoolean(), Random.nextBoolean())
    }

    private fun randomIngredients(): List<Ingredient> {
        return (0..Random.nextInt(5, 20))
                .mapIndexed { index, _ ->
                    Ingredient(
                            "Ingredient $index",
                            Random.nextInt(1, 5),
                            index
                    )
                }
    }

}