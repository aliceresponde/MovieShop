package com.example.movieshop.ui.movielist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.movieshop.repository.MoviesRepository
import com.example.movieshop.ui.model.MovieItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesListViewModel @ViewModelInject constructor(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: MoviesRepository
) : ViewModel() {
    val movies: LiveData<List<MovieItem>> =
        repository.getAllMoviesFlow().asLiveData(coroutineDispatcher)

    init {
        viewModelScope.launch(coroutineDispatcher) {
            repository.syncData()
        }
    }

    fun addItemToCart(item: MovieItem) {
        viewModelScope.launch(coroutineDispatcher) {
                repository.addItemToCart(item.id, item.quantity + 1)
        }
    }

    fun removeItemFromCard(item: MovieItem) {
        viewModelScope.launch(coroutineDispatcher) {
            repository.removeItemFromCard(item.id, item.quantity - 1)
        }
    }
}