package dev.olog.basil.data.remote

import dagger.Binds
import dagger.Module
import dev.olog.basil.core.RecipeGateway
import dev.olog.basil.data.remote.repository.RecipeRepositoryRemote
import dev.olog.basil.shared.dagger.qualifier.Remote
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class
    ]
)
abstract class RepositoryRemoteModule {

    @Binds
    @Remote
    @Singleton
    internal abstract fun provideRemoteRepository(impl: RecipeRepositoryRemote): RecipeGateway

}