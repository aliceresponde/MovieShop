package com.example.movieshop.data.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomDataSource @Inject constructor(db: MoviesDatabase) : LocalDataSource {
    private val movieDao: MovieDao = db.getMoviesDao()

    override fun getMoviesFlow(): Flow<List<Movie>> {
        return movieDao.getAllMoviesFlow()
    }

    override fun getShoppingMoviesFlow(): Flow<List<Movie>> {
        return movieDao.getAllCartMoviesFlow()
    }

    override suspend fun insertAll(data: List<Movie>) {
        movieDao.insertAll(data)
    }

    override suspend fun addItemToCart(id: Int, quantity: Int) {
        movieDao.updateQuantity(id, quantity)
    }

    override suspend fun removeItemFromCart(id: Int, quantity: Int) {
        movieDao.updateQuantity(id, quantity)
    }

    override suspend fun removeAllItemInCart() {
        movieDao.removeAllItemInCart()
    }
}