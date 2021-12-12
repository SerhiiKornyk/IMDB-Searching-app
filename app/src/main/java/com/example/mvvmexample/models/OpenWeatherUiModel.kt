package com.example.mvvmexample.models


data class OpenWeatherUiModel(
    val id: Int?,
    val latitude: Double?,
    val longitude: Double?,
    val cityName: String?,
    val temperatureK: Double?,
    val feelsTemperatureK: Double?

) {
    companion object {
        @JvmStatic
        fun createModel(dbModel: OpenWeatherDBEntity): OpenWeatherUiModel {
            return OpenWeatherUiModel(
                dbModel.key,
                dbModel.coordinatesLatitude,
                dbModel.coordinatesLongitude,
                dbModel.name,
                dbModel.temperatureK,
                dbModel.feelsTempK
            )
        }

        @JvmStatic
        fun createModel(apiResponse: WeatherApiResponse): OpenWeatherUiModel {
            return OpenWeatherUiModel(
                apiResponse.id,
                apiResponse.coord?.lat,
                apiResponse.coord?.lon,
                apiResponse.name,
                apiResponse.main?.tempK,
                apiResponse.main?.feelsLikeK
            )
        }

        @JvmStatic
        fun createEmptyModel(): OpenWeatherUiModel =
            OpenWeatherUiModel(null, null, null, null, null, null)
    }

    fun getTemperatureInCelsius(): Double? = temperatureK?.minus(273.0)

    fun getFeelsTemperatureInCelsius(): Double? = feelsTemperatureK?.minus(273.0)
}