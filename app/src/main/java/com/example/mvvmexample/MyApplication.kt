package com.example.mvvmexample

import android.app.Application
import android.content.Context
import com.example.mvvmexample.DI.AppComponent
import com.example.mvvmexample.DI.DaggerAppComponent
import com.example.mvvmexample.DI.Modules.RemoteDataSourceModule


class MyApplication : Application() {

     private lateinit var component: AppComponent

    companion object {
        @JvmStatic
        var instance: MyApplication? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
       component = DaggerAppComponent.builder()
           .remoteDataSourceModule(RemoteDataSourceModule())
           .build()

    }

    fun getComponent():AppComponent = component


}