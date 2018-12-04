package dev.olog.basil.data;

import java.util.List;

import dev.olog.basil.domain.entity.Recipe;
import dev.olog.basil.domain.gateway.RecipeGateway;
import io.reactivex.Flowable;

public class RecipeRepository implements RecipeGateway {

    @Override
    public Flowable<List<Recipe>> getAll() {
        return null;
    }

    @Override
    public Flowable<Recipe> getById() {
        return null;
    }
}
