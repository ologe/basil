package dev.olog.basil.presentation.createnew;

import android.content.Context;
import android.net.Uri;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewRecipePresenter implements DefaultLifecycleObserver {

    private Disposable disposable;
    private Context context;
    private final RecipeGateway recipeRepository;

    private Uri image = null;

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    Toast.makeText(context, R.string.new_recipe_created, Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(context, R.string.common_error, Toast.LENGTH_SHORT).show();
                });
    }

    public void setImage(Uri image){
        this.image = image;
    }

    public boolean isImageSet(){
        return image != null;
    }

    public Uri getImage(){
        return image;
    }

}
