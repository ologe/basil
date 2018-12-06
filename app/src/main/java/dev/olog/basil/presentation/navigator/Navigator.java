package dev.olog.basil.presentation.navigator;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import dev.olog.basil.dagger.scope.PerActivity;
import dev.olog.basil.presentation.ingredients.IngredientsFragment;

@PerActivity
public class Navigator {

    private final AppCompatActivity activity;

    @Inject
    Navigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void toIngredientsFragment(long id){
        IngredientsFragment.newInstance(id).show(activity.getSupportFragmentManager(), IngredientsFragment.TAG);
    }

}
