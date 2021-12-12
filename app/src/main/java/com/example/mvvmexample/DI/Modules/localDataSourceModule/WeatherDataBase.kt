package com.example.mvvmexample.DI.Modules.localDataSourceModule

import android.content.Context
import com.example.mvvmexample.database.WeatherDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataSourceModule(private val applicationContext: Context) {

    @Provides
    @Singleton
    fun provideDatabase(): WeatherDataBase = WeatherDataBase.invoke(applicationContext)

    @Provides
    @Singleton
    fun provideWeatherDao(db:WeatherDataBase): WeatherDao = db.dao()
}