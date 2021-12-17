package com.example.mvvmexample.Repo

import com.example.mvvmexample.DI.Modules.remoteModule.ApiInterface
import com.example.mvvmexample.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class WeatherRepo @Inject constructor(
    private val apiInterface: ApiInterface,
) {

    suspend fun getIds(): IdsApiResponse? = apiInterface.getIds()


    suspend fun getObject(id: Int): Flow<ObjectApiResponse> = flow {
        apiInterface.getObject(id = id)?.let {
            emit(it)
        }
    }


}