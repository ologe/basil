package dev.olog.basil.app;

import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import dev.olog.basil.BuildConfig;
import dev.olog.basil.data.RecipeGateway;

public class App extends DaggerApplication {

    @Inject RecipeGateway gateway;

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }

    private void initialize(){
        if (BuildConfig.DEBUG){
//            gateway.populateIfEmpty();
            Stetho.initializeWithDefaults(this);
        }
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
