package dev.olog.basil.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import dev.olog.basil.core.Allergens
import dev.olog.basil.core.Ingredient
import dev.olog.basil.core.RecipeCategory

@Entity(
    tableName = "recipes",
    indices = [Index("id")]
)
data class RecipeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val description: String,
    val allergens: Allergens,
    val category: RecipeCategory,
    val ingredients: List<Ingredient>,
    val image: String
)