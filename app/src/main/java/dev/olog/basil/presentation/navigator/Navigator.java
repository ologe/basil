package dev.olog.basil.presentation.navigator;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import dev.olog.basil.R;
import dev.olog.basil.dagger.scope.PerActivity;
import dev.olog.basil.presentation.detail.DetailFragment;
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

    public void toDetailFragment(){
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, new DetailFragment(), DetailFragment.TAG)
                .commit();
    }

}
