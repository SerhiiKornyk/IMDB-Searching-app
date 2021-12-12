package com.example.mvvmexample.DI

import com.example.mvvmexample.DI.Modules.localDataSourceModule.LocalDataSourceModule
import com.example.mvvmexample.DI.Modules.remoteModule.RemoteDataSourceModule
import com.example.mvvmexample.ui.MainActivity
import com.example.mvvmexample.Repo.WeatherRepo
import com.example.mvvmexample.viewModels.WeatherScreenViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RemoteDataSourceModule::class, LocalDataSourceModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(repo:WeatherRepo)
    fun inject(vm:WeatherScreenViewModel)

}