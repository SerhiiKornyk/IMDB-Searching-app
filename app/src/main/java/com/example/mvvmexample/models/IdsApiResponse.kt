package com.example.mvvmexample.models

import com.google.gson.annotations.SerializedName

data class IdsApiResponse(
    @SerializedName("data")
    val ids: List<IdEntity>
)

data class IdEntity(
    val id: String
)
