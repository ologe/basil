package dev.olog.basil

import android.content.Context
import dagger.Binds
import dagger.Module
import dev.olog.basil.shared.dagger.qualifier.ApplicationContext

@Module
internal abstract class AppModule {

    @Binds
    @ApplicationContext
    internal abstract fun provideContext(instance: App): Context

}