package com.example.mvvmexample.DI.Modules.viewModelModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmexample.viewModels.MainFragmentViewModel
import com.example.mvvmexample.viewModels.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainFragmentViewModel::class)
    abstract fun mainFragmentViewModel(viewModel: MainFragmentViewModel): ViewModel
}