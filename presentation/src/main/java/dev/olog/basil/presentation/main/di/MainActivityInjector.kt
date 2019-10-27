package dev.olog.basil.presentation.main.di

import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import dev.olog.basil.presentation.main.MainActivity

@Module(subcomponents = [MainActivityComponent::class])
abstract class MainActivityInjector {

    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    internal abstract fun bindActivity(factory: MainActivityComponent.Factory): AndroidInjector.Factory<*>

}

