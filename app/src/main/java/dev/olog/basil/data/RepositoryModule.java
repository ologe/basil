package dev.olog.basil.data;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dev.olog.basil.domain.gateway.RecipeGateway;

@Module
public abstract class RepositoryModule  {

    @Binds
    @Singleton
    abstract RecipeGateway provideRecipeRepository(RecipeRepository impl);

}
