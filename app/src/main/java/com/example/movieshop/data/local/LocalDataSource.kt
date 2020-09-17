package com.example.movieshop.data.local

import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getMoviesFlow(): Flow<List<Movie>>
    fun getShoppingMoviesFlow(): Flow<List<Movie>>
    suspend fun insertAll(data: List<Movie>)

    // ================= Chart
    suspend fun addItemToCart(id: Int, quantity: Int)
    suspend fun removeItemFromCart(id: Int, quantity: Int)
    suspend fun removeAllItemInCart()
}
