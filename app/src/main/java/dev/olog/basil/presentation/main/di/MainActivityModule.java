package dev.olog.basil.presentation.main.di;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import dagger.Module;
import dagger.Provides;
import dev.olog.basil.dagger.qualifier.ActivityContext;
import dev.olog.basil.presentation.main.MainActivity;

@Module
public class MainActivityModule {

    private final MainActivity activity;

    MainActivityModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext(){
        return activity;
    }

    @Provides
    AppCompatActivity provideActivity(){
        return activity;
    }

}
