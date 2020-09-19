package com.example.movieshop.repository

import com.example.movieshop.ui.model.MovieItem
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun syncData()
    fun getAllMoviesFlow(): Flow<List<MovieItem>>
    fun getMovieDetailFlow(id: Int): Flow<MovieItem>
    suspend fun removeAllItemInCart()
    //-----------------
    fun getShoppingMoviesFlow(): Flow<List<MovieItem>>
    suspend fun addMovieToCart(id: Int)
    suspend fun removeMovieFromCart(id: Int)
}
