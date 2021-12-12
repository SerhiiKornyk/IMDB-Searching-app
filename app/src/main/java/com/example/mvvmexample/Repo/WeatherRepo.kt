package com.example.mvvmexample.Repo

import android.util.Log
import com.example.mvvmexample.DI.Modules.localDataSourceModule.WeatherDao
import com.example.mvvmexample.DI.Modules.remoteModule.ApiInterface
import com.example.mvvmexample.helper.OPEN_WEATHER_MAP_API_KEY
import com.example.mvvmexample.helper.OPEN_WEATHER_MAP_API_LANGUAGE_UKRAINIAN
import com.example.mvvmexample.models.OpenWeatherDBEntity
import com.example.mvvmexample.models.OpenWeatherUiModel
import com.example.mvvmexample.models.WeatherApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class WeatherRepo @Inject constructor(
    private val apiInterface: ApiInterface,
    private val weatherDao: WeatherDao
) {


    suspend fun getWeather(
        city: String?,
        longitude: Double?,
        latitude: Double?,
    ): Flow<OpenWeatherUiModel>? {
        var resultFlow: Flow<OpenWeatherUiModel>? = emptyFlow()
        val dbModel: OpenWeatherDBEntity? =
            weatherDao.getData(city = city, lon = longitude, lat = latitude)
        if (dbModel != null) {
            resultFlow = flow {
                emit(OpenWeatherUiModel.createModel(dbModel))
            }
        } else {
            val response = apiInterface.getWeather(
                city, latitude, longitude, OPEN_WEATHER_MAP_API_KEY,
                OPEN_WEATHER_MAP_API_LANGUAGE_UKRAINIAN
            )
            response?.let {
                insertData(response)
                resultFlow = flow {
                    emit(
                        OpenWeatherUiModel.createModel(response)
                    )
                }

            }

        }
        return resultFlow
    }


    private suspend fun insertData(apiResponse: WeatherApiResponse) {
        val dbModel = OpenWeatherDBEntity.createModel(apiResponse)
        weatherDao.insertWeather(dbModel)
    }

    suspend fun clearDB() {
        weatherDao.clearDB()
    }
}