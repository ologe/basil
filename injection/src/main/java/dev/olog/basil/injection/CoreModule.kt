package dev.olog.basil.injection

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dev.olog.basil.shared.dagger.qualifier.ApplicationContext

@Module
internal abstract class CoreModule {

    @Binds
    @ApplicationContext
    internal abstract fun provideContext(instance: Application): Context

}