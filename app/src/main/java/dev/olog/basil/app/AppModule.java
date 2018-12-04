package dev.olog.basil.app;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dev.olog.basil.dagger.qualifier.ApplicationContext;

@Module
class AppModule {

    private final App app;

    AppModule(App app) {
        this.app = app;
    }

    @Provides
    @ApplicationContext
    Context provideContext(){
        return app;
    }

}
