package com.example.movieshop.ui.shoppingchart

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.movieshop.repository.MoviesRepository
import com.example.movieshop.ui.model.MovieItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ShoppingChartViewModel @ViewModelInject constructor(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: MoviesRepository
) : ViewModel() {

    private val _loadingVisibility = MutableLiveData<Int>()
    val loadingVisibility: LiveData<Int> get() = _loadingVisibility


    val moviesInCart: LiveData<List<MovieItem>> =
        repository.getShoppingMoviesFlow()
            .onStart {
                _loadingVisibility.postValue(VISIBLE)
            }.onCompletion {
                _loadingVisibility.postValue(GONE)
            }.asLiveData(coroutineDispatcher)

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

    fun removeAllItemInCart() {
        viewModelScope.launch(coroutineDispatcher) {
            repository.removeAllItemInCart()
        }
    }
}
