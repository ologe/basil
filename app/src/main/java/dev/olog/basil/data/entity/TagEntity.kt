package dev.olog.basil.data.entity

import androidx.room.*

@Entity(
        tableName = "tags",
        indices = [Index(value = ["recipe_id", "value"])],
        foreignKeys = [ForeignKey(entity = RecipeEntity::class, parentColumns = arrayOf("id"), childColumns = arrayOf("recipe_id"))]
)
data class TagEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        @ColumnInfo(name = "recipe_id")
        val recipeId: Long,
        val value: String
)
