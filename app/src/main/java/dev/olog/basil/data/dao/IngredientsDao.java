package dev.olog.basil.data.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import dev.olog.basil.data.entity.IngredientEntity;
import io.reactivex.Flowable;

@Dao
public abstract class IngredientsDao {

    @Query("SELECT * FROM ingredients where recipe_id = :recipeId")
    public abstract Flowable<List<IngredientEntity>> observeByRecipeId(long recipeId);

    @Insert
    public abstract void insertGroup(List<IngredientEntity> tags);

}
