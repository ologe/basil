package dev.olog.basil

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dev.olog.basil.data.RepositoryModule
import dev.olog.basil.presentation.main.di.MainActivityInjector
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        RepositoryModule::class,
        AndroidInjectionModule::class,

        MainActivityInjector::class
    ]
)
@Singleton
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<App>

}