package com.example.movieshop.repository

import com.example.movieshop.ui.model.MovieItem
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun syncData()
    fun getAllMoviesFlow(): Flow<List<MovieItem>>
    suspend fun addItemToCart(id: Int, quantity: Int)
    suspend fun removeItemFromCard(id: Int, quantity: Int)

    fun getShoppingMoviesFlow(): Flow<List<MovieItem>>
    suspend fun removeAllItemInCart()
}
