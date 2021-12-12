package com.example.mvvmexample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvmexample.DI.Modules.localDataSourceModule.WeatherDao
import com.example.mvvmexample.helper.WEATHER_DB_NAME
import com.example.mvvmexample.models.OpenWeatherDBEntity


@Database(version = 5, entities = [OpenWeatherDBEntity::class])
abstract class WeatherDataBase : RoomDatabase() {

    abstract fun dao(): WeatherDao

    companion object {
        @Volatile
        var db: WeatherDataBase? = null

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WeatherDataBase::class.java,
                WEATHER_DB_NAME
            ).fallbackToDestructiveMigration().build()

        operator fun invoke(context: Context) = db ?: synchronized(this) {
            db ?: buildDatabase(context).also { db = it }
        }

    }


}