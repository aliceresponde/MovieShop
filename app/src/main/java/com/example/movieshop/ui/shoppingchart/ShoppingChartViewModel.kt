package com.example.movieshop.ui.shoppingchart

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieshop.repository.MoviesRepository
import com.example.movieshop.ui.model.MovieItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoppingChartViewModel @ViewModelInject constructor(
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val repository: MoviesRepository
) : ViewModel() {
    val moviesInCart: LiveData<List<MovieItem>> =
        repository.getShoppingMoviesFlow().asLiveData(coroutineDispatcher)

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

    fun removeAllItemInCart() {
        viewModelScope.launch(coroutineDispatcher) {
            repository.removeAllItemInCart()
        }
    }
}
