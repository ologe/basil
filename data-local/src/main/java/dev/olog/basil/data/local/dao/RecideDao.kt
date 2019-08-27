package dev.olog.basil.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.olog.basil.data.local.entity.RecipeEntity

@Dao
abstract class RecideDao {

    @Query(
        """
        SELECT *
        FROM recipes
    """
    )
    abstract suspend fun getAll(): List<RecipeEntity>

    @Query(
        """
        SELECT *
        FROM recipes
        WHERE id = :id
    """
    )
    abstract suspend fun getById(id: Int): RecipeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertGroup(entities: List<RecipeEntity>)

}