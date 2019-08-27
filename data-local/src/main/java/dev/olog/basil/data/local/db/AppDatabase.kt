package dev.olog.basil.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.olog.basil.data.local.dao.RecideDao
import dev.olog.basil.data.local.entity.RecipeEntity

@Database(
    entities = [
        RecipeEntity::class
    ], version = 1, exportSchema = true
)
@TypeConverters(CustomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecideDao

}