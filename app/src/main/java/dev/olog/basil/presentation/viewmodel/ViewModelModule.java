package dev.olog.basil.presentation.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dev.olog.basil.dagger.ViewModelKey;
import dev.olog.basil.presentation.main.MainFragmentViewModel;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory provideViewModelFactory(AppViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(MainFragmentViewModel.class)
    abstract ViewModel provideMainFragmentViewModel(MainFragmentViewModel viewModel);

}
