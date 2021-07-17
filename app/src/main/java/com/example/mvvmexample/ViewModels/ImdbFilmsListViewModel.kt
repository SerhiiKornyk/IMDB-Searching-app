package com.example.mvvmexample.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmexample.models.TVEntityUI
import com.example.mvvmexample.Repo.ImdbRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class ImdbFilmsListViewModel(
    private val repo: ImdbRepo
) : ViewModel() {

    private  var queryResult: Flow<TVEntityUI>? = emptyFlow()

     suspend fun getShows(query: String): Flow<TVEntityUI>? {
        queryResult = viewModelScope.async(Dispatchers.IO) {
             repo.getFilm(query)
        }.await()
            return queryResult
    }
}