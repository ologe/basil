package dev.olog.basil.data.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import dev.olog.basil.data.entity.ImageEntity;
import io.reactivex.Flowable;

@Dao
public abstract class ImagesDao {

    @Query("SELECT * FROM images where recipe_id = :recipeId")
    public abstract Flowable<List<ImageEntity>> observeByRecipeId(long recipeId);

    @Insert
    public abstract void insertGroup(List<ImageEntity> tags);

}
