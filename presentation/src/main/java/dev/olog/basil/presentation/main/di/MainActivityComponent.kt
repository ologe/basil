package dev.olog.basil.presentation.main.di

import dagger.Subcomponent
import dagger.android.AndroidInjector
import dev.olog.basil.presentation.ViewModelModule
import dev.olog.basil.presentation.main.MainActivity
import dev.olog.basil.shared.dagger.scope.PerActivity

@Subcomponent(
    modules = [
        ViewModelModule::class,
        MainActivityModule::class,
        AndroidBindingsModule::class
    ]
)
@PerActivity
interface MainActivityComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Factory
    interface Factory : AndroidInjector.Factory<MainActivity>

}