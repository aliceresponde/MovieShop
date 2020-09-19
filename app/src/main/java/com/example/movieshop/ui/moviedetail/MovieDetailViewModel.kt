package com.example.movieshop.ui.moviedetail

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.movieshop.repository.MoviesRepository
import com.example.movieshop.ui.model.MovieItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MovieDetailViewModel @ViewModelInject constructor(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: MoviesRepository
) : ViewModel() {

    private val _movie = MutableLiveData<MovieItem>()
    val movie: LiveData<MovieItem> get() = _movie

    private val _loadingVisibility = MutableLiveData<Int>()
    val loadingVisibility: LiveData<Int> get() = _loadingVisibility

    private val _itemNotExistVisibility = MutableLiveData<Int>()
    val itemNotExistVisibility: LiveData<Int> get() = _itemNotExistVisibility

    fun getMovieDetailFlow(id: Int) = repository.getMovieDetailFlow(id)
        .onStart {
            _loadingVisibility.postValue(VISIBLE)
            delay(500)
        }.catch {
            _itemNotExistVisibility.postValue(VISIBLE)
        }.onCompletion {
            _loadingVisibility.postValue(GONE)
        }.asLiveData()

    fun addItemToCart(id: Int) {
        viewModelScope.launch(coroutineDispatcher) {
            repository.addMovieToCart(id)
        }
    }

    fun removeToCart(movieId: Int) {
        viewModelScope.launch(coroutineDispatcher) {
            repository.removeMovieFromCart(movieId)
        }
    }
}
