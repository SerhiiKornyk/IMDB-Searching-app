package com.example.mvvmexample.DI.Modules.remoteModule

import com.example.mvvmexample.models.IdsApiResponse
import com.example.mvvmexample.models.ObjectApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("/api/v1/entities/getAllIds")
    suspend fun getIds(): IdsApiResponse?

    @GET("/api/v1/object/{id}")
    suspend fun getObject(
        @Path("id") id: Int
    ): ObjectApiResponse?
}