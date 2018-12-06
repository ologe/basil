package dev.olog.basil.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import dev.olog.basil.data.dao.ImagesDao;
import dev.olog.basil.data.dao.IngredientsDao;
import dev.olog.basil.data.dao.RecipesDao;
import dev.olog.basil.data.dao.TagsDao;
import dev.olog.basil.data.entity.ImageEntity;
import dev.olog.basil.data.entity.IngredientEntity;
import dev.olog.basil.data.entity.RecipeEntity;
import dev.olog.basil.data.entity.TagEntity;

@Database(entities = {
        RecipeEntity.class,
        IngredientEntity.class,
        TagEntity.class,
        ImageEntity.class
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract RecipesDao recipeDao();
    public abstract IngredientsDao ingredientsDao();
    public abstract TagsDao tagDao();
    public abstract ImagesDao imagesDao();

}
