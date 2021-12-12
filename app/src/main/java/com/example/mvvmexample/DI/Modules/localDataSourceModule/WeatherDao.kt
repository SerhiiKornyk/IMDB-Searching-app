package com.example.mvvmexample.DI.Modules.localDataSourceModule

import androidx.room.*
import com.example.mvvmexample.models.OpenWeatherDBEntity

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(entity: OpenWeatherDBEntity)

    @Query("SELECT * from weatherDB Where city_name =:city OR (lon =:lon AND lat =:lat)")
    suspend fun getData(city: String?, lon: Double?, lat: Double?): OpenWeatherDBEntity?

    @Query("SELECT * from weatherDB Where city_name =:city")
    suspend fun getData(city: String?): OpenWeatherDBEntity?

    @Query("DELETE from weatherDB")
    suspend fun clearDB()
}