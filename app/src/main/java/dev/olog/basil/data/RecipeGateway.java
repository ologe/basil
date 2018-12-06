package dev.olog.basil.data;

import java.util.List;

import dev.olog.basil.domain.entity.Recipe;
import io.reactivex.Flowable;

public interface RecipeGateway {

    Flowable<List<Recipe>> observeAll();
    Flowable<Recipe> observeById(long id);

    void populateIfEmpty();

}
