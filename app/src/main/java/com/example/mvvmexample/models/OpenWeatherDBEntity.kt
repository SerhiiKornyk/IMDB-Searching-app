package com.example.mvvmexample.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "weatherDB")
data class OpenWeatherDBEntity(
    @PrimaryKey(autoGenerate = true)
    val key: Int?,
    @ColumnInfo(name = "lon")
    val coordinatesLongitude: Double?,
    @ColumnInfo(name = "lat")
    val coordinatesLatitude: Double?,
    val temperatureK: Double?,
    val feelsTempK: Double?,
    val timeZone: Int?,
    @ColumnInfo(name = "city_name")
    val name: String?
) : Serializable {
    companion object {
        @JvmStatic
        fun createModel(apiResponse: WeatherApiResponse): OpenWeatherDBEntity {
            return OpenWeatherDBEntity(
                key = apiResponse.id,
                coordinatesLongitude = apiResponse.coord?.lon,
                coordinatesLatitude = apiResponse.coord?.lat,
                timeZone = apiResponse.timezone,
                name = apiResponse.name,
                temperatureK = apiResponse.main?.tempK,
                feelsTempK = apiResponse.main?.feelsLikeK
            )
        }
    }

}
