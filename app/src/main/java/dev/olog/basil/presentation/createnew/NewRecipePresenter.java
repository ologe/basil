package dev.olog.basil.presentation.createnew;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import dev.olog.basil.R;
import dev.olog.basil.dagger.qualifier.ApplicationContext;
import dev.olog.basil.data.RecipeGateway;
import dev.olog.basil.domain.entity.Recipe;
import io.reactivex.disposables.Disposable;

public class NewRecipePresenter implements DefaultLifecycleObserver {

    private Disposable disposable;
    private Context context;
    private final RecipeGateway recipeRepository;

    @Inject NewRecipePresenter(@ApplicationContext Context context,
                               Lifecycle lifecycle,
                               RecipeGateway recipeRepository) {
        this.context = context;
        lifecycle.addObserver(this);
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        if (disposable != null){
            disposable.dispose();
        }
    }

    public void saveRecipe(Recipe recipe){
        if (disposable != null){
            disposable.dispose();
        }
        disposable = recipeRepository.saveRecipe(recipe)
                .subscribe(() -> {
                    Toast.makeText(context, R.string.new_recipe_created, Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(context, R.string.common_error, Toast.LENGTH_SHORT).show();
                });
    }

}
