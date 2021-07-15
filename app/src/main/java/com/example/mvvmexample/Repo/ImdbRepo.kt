package com.example.mvvmexample.Repo

import android.util.Log
import com.example.mvvmexample.REmoteDataSourceApi.ApiInterface
import com.example.mvvmexample.Helper.RAPID_API_IMDB_HOST
import com.example.mvvmexample.Helper.RAPID_API_KEY
import com.example.mvvmexample.Models.TVEntityUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

import javax.inject.Inject

class ImdbRepo @Inject constructor(private val apiInterface: ApiInterface) {


    suspend fun getFilm(): Flow<TVEntityUI> {
        Log.d("TAG", "getFilm: Sending request")
        return apiInterface.getFilm(
            RAPID_API_IMDB_HOST,
            RAPID_API_KEY
        ).entitiesUI.asFlow()

    }
}