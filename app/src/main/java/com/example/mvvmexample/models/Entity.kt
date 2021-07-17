package com.example.mvvmexample.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TVEntityApi(
    @SerializedName("d")
    val entitiesUI:ArrayList<TVEntityUI>?,
    @SerializedName("q")
    val query:String?,
    val v:String?
    ):Parcelable






@Parcelize
data class TVEntityUI(
    val id: String?,
    @SerializedName("l")
    val name: String?,
    @SerializedName("q")
    val category: String?,
    val rank: String?,
    @SerializedName("s")
    val actor: String?,
    @SerializedName("y")
    val launchYear: String?,
    @SerializedName("yr")
    val years: String?


) : Parcelable {
    var actors = ArrayList<String>(actor?.split(","))
}