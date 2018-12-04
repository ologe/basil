package dev.olog.basil.presentation.main.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dev.olog.basil.presentation.detail.DetailFragment;
import dev.olog.basil.presentation.main.MainFragment;

@Module
public abstract class FragmentBindings {

    @ContributesAndroidInjector
    abstract MainFragment provideMainFragment();

    @ContributesAndroidInjector
    abstract DetailFragment provideDetailFragment();

}
