package com.example.mvvmexample.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mvvmexample.MyApplication
import com.example.mvvmexample.R
import com.example.mvvmexample.viewModels.WeatherScreenViewModel
import com.example.mvvmexample.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private val TAG = "MAIN_ACTIVITY"
    private lateinit var locationClient: FusedLocationProviderClient

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: WeatherScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        MyApplication.instance?.getComponent()?.inject(this)
        viewModel = ViewModelProvider(this).get(WeatherScreenViewModel::class.java)
        binding.vm = viewModel
        binding.clickHandler = WeatherActivityClickHandler()
        configPermission()
        subscribeUi()
    }

    private fun configUi() {
        binding.spLocation.apply {
            adapter = ArrayAdapter(
                this@MainActivity,
                android.R.layout.simple_spinner_dropdown_item,
                if (viewModel.permissionGranted) resources.getStringArray(R.array.locations_granted) else resources.getStringArray(
                    R.array.locations_denied
                )
            )
        }


    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (!viewModel.permissionGranted) return

        locationClient = LocationServices.getFusedLocationProviderClient(this)
        locationClient.lastLocation.addOnSuccessListener {
            viewModel.uiModel.set(
                viewModel.uiModel.get()?.copy(latitude = it.latitude, longitude = it.longitude)
            )
            Log.d(TAG, "getLocation: 12345")
            viewModel.currentUserLocation.set(LatLng(it.latitude, it.longitude))
        }

    }

    private fun subscribeUi() {
        lifecycleScope.launchWhenStarted {
            viewModel.cleatDB()
            viewModel.weatherScreenUiState.collect { state ->
                when (state) {
                    MainActivityUiState.InitState -> {
                        // show louder
                    }
                    is MainActivityUiState.ErrorState -> {
                        state.exception.printStackTrace()
                    }
                    is MainActivityUiState.NoPermissionGrantedState -> {
                        viewModel.uiModel.set(state.weatherUiEntity)

                    }
                    is MainActivityUiState.PermissionGrantedState -> {
                        viewModel.uiModel.set(state.weatherUiEntity)
                    }
                }
            }
        }
    }

    private fun getWeather(item: String) {
        when (item) {
            getString(R.string.your_position) -> {
                Log.d(TAG, "getWeather: ${viewModel.currentUserLocation.get()}")
                if (viewModel.currentUserLocation.get()?.longitude == null) return
                if (viewModel.currentUserLocation.get()?.latitude == null) return

                viewModel.getWeather(
                    lon = viewModel.currentUserLocation.get()?.longitude,
                    lat = viewModel.currentUserLocation.get()?.latitude,
                    city = null,
                    permissionGranted = viewModel.permissionGranted
                )
            }
            else -> {
                viewModel.getWeather(city = item, null, null, viewModel.permissionGranted)
            }
        }
    }

    private fun configPermission() {
        val permission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                when {
                    granted -> {
                        viewModel.permissionGranted = true
                        getLocation()
                    }

                }
                configUi()
            }

        permission.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    inner class WeatherActivityClickHandler() {
        fun getWeather(view: View) {
            getWeather(binding.spLocation.selectedItem.toString())
        }
    }


}