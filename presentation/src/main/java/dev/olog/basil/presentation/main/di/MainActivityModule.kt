package dev.olog.basil.presentation.main.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.olog.basil.presentation.ViewModelKey
import dev.olog.basil.presentation.main.MainFragmentViewModel

@Module
abstract class MainActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainFragmentViewModel::class)
    internal abstract fun provideAlbumViewModel(viewModel: MainFragmentViewModel): ViewModel

}