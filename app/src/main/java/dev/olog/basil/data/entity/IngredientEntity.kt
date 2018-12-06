package dev.olog.basil.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ingredients",
    indices = [Index(value = ["recipe_id", "name"])],
    foreignKeys = [ForeignKey(entity = RecipeEntity::class, parentColumns = arrayOf("id"), childColumns = arrayOf("recipe_id"))]
)
class IngredientEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        @ColumnInfo(name = "recipe_id")
        val recipeId: Long,
        val name: String,
        val quantity: Int,
        val order: Int
)
