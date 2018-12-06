package dev.olog.basil.data.entity;

import java.util.List;
import java.util.Random;

import androidx.core.math.MathUtils;
import io.reactivex.Observable;

public class FakeEntityFactory {

    public static RecipeEntity mockRecipe(int index) {
        return new RecipeEntity(
                index,
                "Recipe " + index,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                MathUtils.clamp(new Random().nextInt(8), 1, 8),
                MathUtils.clamp(new Random().nextInt(2000), 100, 2000)
        );
    }

    public static IngredientEntity mockIngredient(long recipeId, int index){
        return new IngredientEntity(
                0, recipeId, "Ingredient " + index,
                MathUtils.clamp(new Random().nextInt(10), 1 , 10),
                index
        );
    }

    public static TagEntity mockTag(long recipeId, int index){
        return new TagEntity(
                0, recipeId,"Tag " + index
        );
    }

    public static ImageEntity mockImage(long recipeId, int index){
        return new ImageEntity(
                0, recipeId, ""
        );
    }

    public static List<IngredientEntity> mockIngredientsForRecipe(long recipeId){
        return Observable.range(1, MathUtils.clamp(new Random().nextInt(8), 1, 8))
                .map(index -> FakeEntityFactory.mockIngredient(recipeId, index))
                .toList()
                .blockingGet();
    }

    public static List<TagEntity> mockTagsForRecipe(long recipeId){
        return Observable.range(1, MathUtils.clamp(new Random().nextInt(8), 1, 4))
                .map(index -> FakeEntityFactory.mockTag(recipeId, index))
                .toList()
                .blockingGet();
    }

    public static List<ImageEntity> mockImagesForRecipe(long recipeId){
        return Observable.range(1, MathUtils.clamp(new Random().nextInt(8), 1, 3))
                .map(index -> FakeEntityFactory.mockImage(recipeId, index))
                .toList()
                .blockingGet();
    }

}
