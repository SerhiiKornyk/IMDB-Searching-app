package com.example.mvvmexample.REmoteDataSourceApi

import com.example.mvvmexample.models.TVEntityApi
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiInterface {

    @GET("https://imdb8.p.rapidapi.com/auto-complete")
     suspend fun getFilm(
        @Header("x-rapidapi-host") host: String,
        @Header("x-rapidapi-key") apiKey: String,
        @Query("q") query:String

        ):TVEntityApi
}