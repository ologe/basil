package dev.olog.basil.data;

import android.content.Context;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import dev.olog.basil.dagger.qualifier.ApplicationContext;
import dev.olog.basil.data.db.AppDatabase;
import dev.olog.basil.data.repository.RecipeRepository;

@Module
public class RepositoryModule  {

    @Provides
    @Singleton
    public static AppDatabase provideRoom(@ApplicationContext Context context){
        return Room.databaseBuilder(context, AppDatabase.class, "db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public static RecipeGateway provideRecipeRepository(RecipeRepository impl) {
        return impl;
    }

}
