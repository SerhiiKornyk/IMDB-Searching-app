package com.example.mvvmexample.ui

import com.example.mvvmexample.models.OpenWeatherUiModel
import com.google.gson.JsonElement

sealed class MainActivityUiState {
    object InitState : MainActivityUiState()
    data class ErrorState(val exception: Throwable) : MainActivityUiState()
    data class NoPermissionGrantedState(val weatherUiEntity: OpenWeatherUiModel) : MainActivityUiState()
    data class PermissionGrantedState(val weatherUiEntity: OpenWeatherUiModel) : MainActivityUiState()
}
