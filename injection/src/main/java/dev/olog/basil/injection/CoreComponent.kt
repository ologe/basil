package dev.olog.basil.injection

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dev.olog.basil.core.RecipeGateway
import dev.olog.basil.data.RepositoryModule
import dev.olog.basil.shared.dagger.qualifier.ApplicationContext
import javax.inject.Singleton

@Component(modules = [
    CoreModule::class,
    RepositoryModule::class
])
@Singleton
interface CoreComponent {

    @ApplicationContext
    fun context(): Context

    fun recipeGateway(): RecipeGateway

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance instance: Application): CoreComponent
    }

    companion object {
        private var component: CoreComponent? = null

        @JvmStatic
        fun coreComponent(application: Application): CoreComponent {
            if (component == null) {
                component = DaggerCoreComponent.factory().create(application)
            }
            return component!!
        }
    }

}