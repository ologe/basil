package dev.olog.basil.app;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dev.olog.basil.data.RepositoryModule;
import dev.olog.basil.presentation.main.di.MainActivityInjector;
import dev.olog.basil.presentation.viewmodel.ViewModelModule;

@Component(modules = {
        AppModule.class,
        AndroidSupportInjectionModule.class,
        ViewModelModule.class,

//        data
        RepositoryModule.class,

//        presentation
        MainActivityInjector.class
})
@Singleton
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App> {

        public abstract Builder module(AppModule module);

        @Override
        public void seedInstance(App instance) {
            module(new AppModule(instance));
        }
    }

}
