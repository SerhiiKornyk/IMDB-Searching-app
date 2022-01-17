package com.example.mvvmexample.ui

import com.example.mvvmexample.models.ObjectApiResponse

sealed class MainFragmentUiState {
    object InitState : MainFragmentUiState()
    data class ErrorState(val exception: Throwable) : MainFragmentUiState()
    data class SuccessState(val objectApiResponse: ObjectApiResponse) : MainFragmentUiState()
}
