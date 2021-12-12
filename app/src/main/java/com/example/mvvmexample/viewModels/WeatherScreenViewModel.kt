package com.example.mvvmexample.viewModels

import android.location.Location
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmexample.MyApplication
import com.example.mvvmexample.Repo.WeatherRepo
import com.example.mvvmexample.models.OpenWeatherUiModel
import com.example.mvvmexample.ui.MainActivityUiState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherScreenViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var repo: WeatherRepo

    private val _weatherScreenUiState: MutableStateFlow<MainActivityUiState> =
        MutableStateFlow(MainActivityUiState.InitState)
    val weatherScreenUiState = _weatherScreenUiState.asStateFlow()

    val uiModel: ObservableField<OpenWeatherUiModel> = ObservableField(OpenWeatherUiModel.createEmptyModel())
    var permissionGranted: Boolean = false
    val currentUserLocation: ObservableField<LatLng> = ObservableField()


    init {
        MyApplication.instance?.getComponent()?.inject(this)
    }

    fun getWeather(city: String?, lon: Double?, lat: Double?, permissionGranted: Boolean) {
        viewModelScope.launch {
            repo.getWeather(city = city, longitude = lon, latitude = lat)
                ?.onStart {
                    _weatherScreenUiState.emit(MainActivityUiState.InitState)
                }
                ?.catch {
                    _weatherScreenUiState.emit(MainActivityUiState.ErrorState(it))
                }
                ?.collect {
                    if (permissionGranted) {
                        _weatherScreenUiState.emit(MainActivityUiState.PermissionGrantedState(it))
                    } else {
                        _weatherScreenUiState.emit(MainActivityUiState.NoPermissionGrantedState(it))
                    }
                }
        }
    }

    suspend fun cleatDB() {
        repo.clearDB()
    }
}
