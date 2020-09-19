package com.example.movieshop.data.local

import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getMoviesFlow(): Flow<List<Movie>>
    fun getMovieDetailFlow(id: Int): Flow<Movie>
    suspend fun insertAll(data: List<Movie>)
    suspend fun getMovieDetailBy(id: Int): Movie?
    // ================= Chart
    fun getShoppingMoviesFlow(): Flow<List<Movie>>
    suspend fun removeAllItemInCart()

    // ----------- only with id
    suspend fun addMovieToCar(id: Int)
    suspend fun removeMovieFromCart(id: Int)
}
