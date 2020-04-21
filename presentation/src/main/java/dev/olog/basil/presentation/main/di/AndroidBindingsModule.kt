package dev.olog.basil.presentation.main.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.olog.basil.presentation.detail.DetailFragment
import dev.olog.basil.presentation.detail.DirectionsFragment
import dev.olog.basil.presentation.drawer.DrawerFragment
import dev.olog.basil.presentation.detail.IngredientsFragment
import dev.olog.basil.presentation.pager.PagerFragment
import dev.olog.basil.presentation.recipe.RecipeFragment

@Module
internal abstract class AndroidBindingsModule {

    @ContributesAndroidInjector
    internal abstract fun providePagerFragment(): PagerFragment

    @ContributesAndroidInjector
    internal abstract fun provideRecipeFragment(): RecipeFragment

    @ContributesAndroidInjector
    internal abstract fun provideDetailFragment(): DetailFragment

    @ContributesAndroidInjector
    internal abstract fun provideRecipeTypeFragment(): DrawerFragment

    @ContributesAndroidInjector
    internal abstract fun provideIngredientsFragment(): IngredientsFragment

    @ContributesAndroidInjector
    internal abstract fun provideDirectionsFragment(): DirectionsFragment

}