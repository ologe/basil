package dev.olog.basil.presentation.navigator;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import dev.olog.basil.dagger.scope.PerActivity;
import dev.olog.basil.presentation.createnew.NewRecipeFragment;
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

    public void newRecipe() {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, new NewRecipeFragment(), NewRecipeFragment.TAG)
                .addToBackStack(NewRecipeFragment.TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
