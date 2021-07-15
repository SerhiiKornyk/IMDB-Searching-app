package com.example.mvvmexample.DI

import com.example.mvvmexample.DI.Modules.RemoteDataSourceModule
import com.example.mvvmexample.MainActivity
import com.example.mvvmexample.Repo.ImdbRepo
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RemoteDataSourceModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(repo:ImdbRepo)

}