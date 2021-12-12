package com.example.mvvmexample.DI.Modules.remoteModule

import com.example.mvvmexample.models.WeatherApiResponse
import com.google.gson.JsonElement
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/data/2.5/weather")
    suspend fun getWeather(
        @Query("q") cityName: String?,
        @Query("lat") latitude: Double?,
        @Query("lon") longitude: Double?,
        @Query("appid") apiKey: String,
        @Query("lang") language: String
    ): WeatherApiResponse?
}