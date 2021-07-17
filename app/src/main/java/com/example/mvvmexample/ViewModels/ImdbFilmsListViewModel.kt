package com.example.mvvmexample.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmexample.Models.TVEntityUI
import com.example.mvvmexample.Repo.ImdbRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ImdbFilmsListViewModel(
    private val repo: ImdbRepo
) : ViewModel() {

    private lateinit var queryResult: Flow<TVEntityUI?>

     suspend fun getShows(query: String): Flow<TVEntityUI?> {
      return  viewModelScope.async(Dispatchers.IO) {
             repo.getFilm(query)
        }.await()

    }
}