package dev.olog.basil.presentation.main.di;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dev.olog.basil.dagger.scope.PerActivity;
import dev.olog.basil.presentation.main.MainActivity;

@Subcomponent(modules = {
        MainActivityModule.class,
        FragmentBindings.class
})
@PerActivity
public interface MainActivitySubComponent extends AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {

        public abstract Builder module(MainActivityModule module);

        @Override
        public void seedInstance(MainActivity instance) {
            module(new MainActivityModule(instance));
        }
    }

}
