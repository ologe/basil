package dev.olog.basil.data.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import dev.olog.basil.data.entity.RecipeEntity;
import io.reactivex.Flowable;

@Dao
public abstract class RecipesDao {

    @Query("SELECT * from recipes order by id desc")
    public abstract Flowable<List<RecipeEntity>> observeAllRecipes();

    @Query("SELECT * from recipes where id = :id")
    public abstract Flowable<RecipeEntity> observeById(long id);

    @Insert
    public abstract void insertGroup(List<RecipeEntity> recipes);

    @Query("DELETE FROM recipes")
    public abstract void deleteAll();


}
