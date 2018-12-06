package dev.olog.basil.data.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import dev.olog.basil.data.entity.TagEntity;
import io.reactivex.Flowable;

@Dao
public abstract class TagsDao {

    @Query("SELECT * FROM tags where recipe_id = :recipeId")
    public abstract Flowable<List<TagEntity>> observeByRecipeId(long recipeId);

    @Insert
    public abstract void insertGroup(List<TagEntity> tags);

}
