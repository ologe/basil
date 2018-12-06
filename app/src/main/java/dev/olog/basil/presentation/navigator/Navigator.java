package dev.olog.basil.presentation.navigator;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import dev.olog.basil.R;
import dev.olog.basil.dagger.scope.PerActivity;
import dev.olog.basil.presentation.ingredients.IngredientsFragment;
import dev.olog.basil.presentation.main.MainFragment;

@PerActivity
public class Navigator {

    private final AppCompatActivity activity;

    @Inject
    Navigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void toMainFragment(){
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, new MainFragment(), MainFragment.TAG)
                .commit();
    }

    public void toIngredientsFragment(long id){
        IngredientsFragment.newInstance(id).show(activity.getSupportFragmentManager(), IngredientsFragment.TAG);
    }

}
