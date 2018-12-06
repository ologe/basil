package dev.olog.basil.data.repository;

import java.util.List;

import javax.inject.Inject;

import dev.olog.basil.data.RecipeGateway;
import dev.olog.basil.data.dao.ImagesDao;
import dev.olog.basil.data.dao.IngredientsDao;
import dev.olog.basil.data.dao.RecipesDao;
import dev.olog.basil.data.dao.TagsDao;
import dev.olog.basil.data.db.AppDatabase;
import dev.olog.basil.data.entity.FakeEntityFactory;
import dev.olog.basil.data.entity.ImageEntity;
import dev.olog.basil.data.entity.IngredientEntity;
import dev.olog.basil.data.entity.RecipeEntity;
import dev.olog.basil.data.entity.TagEntity;
import dev.olog.basil.domain.entity.Ingredient;
import dev.olog.basil.domain.entity.Recipe;
import dev.olog.basil.domain.entity.Tag;
import dev.olog.basil.utils.ListUtils;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class RecipeRepository implements RecipeGateway {

    private final RecipesDao recipesDao;
    private final TagsDao tagsDao;
    private final IngredientsDao ingredientsDao;
    private final ImagesDao imagesDao;

    @Inject RecipeRepository(AppDatabase database) {
        this.recipesDao = database.recipeDao();
        this.tagsDao = database.tagDao();
        this.ingredientsDao = database.ingredientsDao();
        this.imagesDao = database.imagesDao();
    }

    @Override
    public Flowable<List<Recipe>> observeAll() {
        return recipesDao.observeAllRecipes()
                .switchMapSingle(recipeList -> Flowable.fromIterable(recipeList)
                        .concatMapSingle(recipe -> Single.zip(
                                ingredientsDao.observeByRecipeId(recipe.getId()).firstOrError(),
                                imagesDao.observeByRecipeId(recipe.getId()).firstOrError(),
                                tagsDao.observeByRecipeId(recipe.getId()).firstOrError(),
                                (ingredients, images, tags) -> Mapper.recipeMapper(
                                        recipe, ingredients, images, tags
                                )
                        )).toList()
                );
    }

    @Override
    public Flowable<Recipe> observeById(long id) {
        return Flowable.combineLatest(
                recipesDao.observeById(id),
                ingredientsDao.observeByRecipeId(id),
                imagesDao.observeByRecipeId(id),
                tagsDao.observeByRecipeId(id),
                Mapper::recipeMapper
        );
    }

    @Override
    public void populateIfEmpty() {
//        recipesDao.deleteAll();

        if (observeAll().blockingFirst().isEmpty()){
            List<RecipeEntity> recipes = Observable.range(1, 10)
                .map(FakeEntityFactory::mockRecipe)
                .toList()
                .blockingGet();
            recipesDao.insertGroup(recipes);

            for (RecipeEntity recipe : recipes) {
                ingredientsDao.insertGroup(FakeEntityFactory.mockIngredientsForRecipe(recipe.getId()));
                tagsDao.insertGroup(FakeEntityFactory.mockTagsForRecipe(recipe.getId()));
                imagesDao.insertGroup(FakeEntityFactory.mockImagesForRecipe(recipe.getId()));
            }

        }
    }

    private static class Mapper {

        static Recipe recipeMapper(RecipeEntity recipe, List<IngredientEntity> ingredients, List<ImageEntity> images, List<TagEntity> tags){
            return new Recipe(
                    recipe.getId(),
                    recipe.getName(),
                    recipe.getDescription(),
                    recipe.getPeople(),
                    recipe.getCalories(),
                    ListUtils.map(ingredients, Mapper::toDomain),
                    ListUtils.map(images, ImageEntity::getValue),
                    ListUtils.map(tags, Mapper::toDomain)
            );
        }

        static Ingredient toDomain(IngredientEntity entity){
            return new Ingredient(
                    entity.getRecipeId(),
                    entity.getName(),
                    entity.getQuantity(),
                    entity.getOrder()
            );
        }

        static Tag toDomain(TagEntity entity){
            return new Tag(
                    entity.getRecipeId(),
                    entity.getValue()
            );
        }

    }

}
