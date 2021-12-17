package com.example.mvvmexample.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmexample.Repo.WeatherRepo
import com.example.mvvmexample.models.IdsApiResponse
import com.example.mvvmexample.ui.MainFragmentUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.properties.Delegates

class MainFragmentViewModel @Inject constructor(private val repo: WeatherRepo) : ViewModel() {

    private var ids: IdsApiResponse? = null

    private var objectCounter: Int by Delegates.observable(0) { _, old, new ->
        getObject(new)
    }

    private val _fragmentState: MutableStateFlow<MainFragmentUiState> =
        MutableStateFlow(MainFragmentUiState.InitState)
    val fragmentState = _fragmentState.asStateFlow()

    fun getIds() {
        viewModelScope.launch {
            ids = withContext(Dispatchers.IO) { repo.getIds() }
        }
    }

    private fun getObject(id: Int) {
        viewModelScope.launch {
            repo.getObject(id).catch {
                _fragmentState.emit(MainFragmentUiState.ErrorState(it))

            }.collect {
                _fragmentState.emit(MainFragmentUiState.SuccessState(it))
            }
        }
    }

    fun changeObject() {
        ids?.let {
            if (objectCounter != it.ids.size)
                objectCounter++
            else objectCounter = 1
        }

    }

}
