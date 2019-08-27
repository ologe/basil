package dev.olog.basil.presentation.main.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.olog.basil.presentation.RecipeTypeChooserFragment
import dev.olog.basil.presentation.detail.IngredientsFragment
import dev.olog.basil.presentation.main.MainFragment

@Module
internal abstract class AndroidBindingsModule {

    @ContributesAndroidInjector
    internal abstract fun provideMainFragment(): MainFragment

    @ContributesAndroidInjector
    internal abstract fun provideRecipeTypeFragment(): RecipeTypeChooserFragment

    @ContributesAndroidInjector
    internal abstract fun provideIngredientsFragment(): IngredientsFragment

}