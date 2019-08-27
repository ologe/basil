package dev.olog.basil.data.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.olog.basil.core.Allergens
import dev.olog.basil.core.Ingredient
import dev.olog.basil.core.RecipeCategory

internal object CustomTypeConverters {

    private val gson by lazy { Gson() }

    @TypeConverter
    @JvmStatic
    fun allergensFromString(value: String): Allergens {
        val type = object : TypeToken<Allergens>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    @JvmStatic
    fun allergensToString(allergens: Allergens): String {
        val type = object : TypeToken<Allergens>() {}.type
        return gson.toJson(allergens, type)
    }

    @TypeConverter
    @JvmStatic
    fun recipeCategoryFromString(value: String): RecipeCategory {
        val type = object : TypeToken<RecipeCategory>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    @JvmStatic
    fun recipeCategoryToString(allergens: RecipeCategory): String {
        val type = object : TypeToken<RecipeCategory>() {}.type
        return gson.toJson(allergens, type)
    }

    @TypeConverter
    @JvmStatic
    fun ingredientsFromString(value: String): List<Ingredient> {
        val type = object : TypeToken<List<Ingredient>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    @JvmStatic
    fun ingredientsCategoryToString(ingredients: List<Ingredient>): String {
        val type = object : TypeToken<List<Ingredient>>() {}.type
        return gson.toJson(ingredients, type)
    }

}