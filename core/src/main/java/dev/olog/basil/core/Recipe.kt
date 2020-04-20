package dev.olog.basil.core

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    val id: Int,
    val name: String,
    val description: String,
    val allergens: Allergens,
    val category: RecipeCategory,
    val ingredients: List<Ingredient>,
    val image: String
): Parcelable

@Parcelize
data class Ingredient(
    val name: String,
    val quantity: Int,
    val order: Int
): Parcelable

@Parcelize
data class Allergens(
    val dairyFree: Boolean,
    val glutenFree: Boolean,
    val ketogenic: Boolean,
    val vegan: Boolean,
    val vegetarian: Boolean
): Parcelable

enum class RecipeCategory {
    Appetizer,
    Entree,
    Dessert,
    Cocktail
}