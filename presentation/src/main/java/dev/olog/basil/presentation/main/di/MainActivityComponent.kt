package dev.olog.basil.presentation.main.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dev.olog.basil.injection.CoreComponent
import dev.olog.basil.presentation.ViewModelModule
import dev.olog.basil.presentation.main.MainActivity
import dev.olog.basil.shared.dagger.scope.PerActivity


internal fun MainActivity.inject() {
    DaggerMainActivityComponent.factory()
        .create(this, CoreComponent.coreComponent(application))
        .inject(this)
}

@Component(
    modules = [
        AndroidInjectionModule::class,
        ViewModelModule::class,
        AndroidBindingsModule::class,
        MainActivityModule::class
    ],
    dependencies = [CoreComponent::class]
)
@PerActivity
internal interface MainActivityComponent {

    fun inject(instance: MainActivity)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance instance: MainActivity, component: CoreComponent): MainActivityComponent
    }

}