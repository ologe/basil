package dev.olog.basil.data

import dagger.Binds
import dagger.Module
import dev.olog.basil.core.RecipeGateway
import dev.olog.basil.data.local.RepositoryLocalModule
import dev.olog.basil.data.remote.RepositoryRemoteModule
import javax.inject.Singleton

@Module(includes = [
    RepositoryLocalModule::class,
    RepositoryRemoteModule::class
])
abstract class RepositoryModule {

    @Binds
    @Singleton
    internal abstract fun provideRepository(impl: RecipeRepository): RecipeGateway

}