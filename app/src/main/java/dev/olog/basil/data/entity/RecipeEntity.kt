package dev.olog.basil.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
        tableName = "recipes",
        indices = [Index(value = ["name"], unique = true)]
)
data class RecipeEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        val name: String,
        val description: String,
        val people: Int,
        val calories: Int
)
