package com.example.movieshop.ui.movielist

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.movieshop.repository.MoviesRepository
import com.example.movieshop.ui.model.MovieItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MoviesListViewModel @ViewModelInject constructor(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: MoviesRepository
) : ViewModel() {
    private val _loadingVisibility = MutableLiveData<Int>()
    val loadingVisibility: LiveData<Int> get() = _loadingVisibility

    val movies: LiveData<List<MovieItem>> =
        repository.getAllMoviesFlow().asLiveData(coroutineDispatcher)

    init {
        viewModelScope.launch(coroutineDispatcher) {
            _loadingVisibility.postValue(VISIBLE)
            delay(200)
            repository.syncData()
            _loadingVisibility.postValue(GONE)
        }
    }

    fun addItemToCart(id: Int) {
        viewModelScope.launch(coroutineDispatcher) {
            repository.addMovieToCart(id)
        }
    }

    fun removeItemFromCard(id: Int) {
        viewModelScope.launch(coroutineDispatcher) {
            repository.removeMovieFromCart(id)
        }
    }
}