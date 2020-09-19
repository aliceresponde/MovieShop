package com.example.movieshop.data.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomDataSource @Inject constructor(db: MoviesDatabase) : LocalDataSource {
    private val movieDao: MovieDao = db.getMoviesDao()

    override fun getMoviesFlow(): Flow<List<Movie>> {
        return movieDao.getAllMoviesFlow()
    }

    override fun getMovieDetailFlow(id: Int): Flow<Movie> {
        return movieDao.getMovieDetailFlow(id)
    }

    override fun getShoppingMoviesFlow(): Flow<List<Movie>> {
        return movieDao.getAllCartMoviesFlow()
    }

    override suspend fun insertAll(data: List<Movie>) {
        movieDao.insertAll(data)
    }

    override suspend fun getMovieDetailBy(id: Int): Movie? {
        return movieDao.getMovieDetailBy(id)
    }

    override suspend fun removeAllItemInCart() {
        movieDao.removeAllItemInCart()
    }

    override suspend fun addMovieToCar(id: Int) {
        movieDao.addITemToChart(id)
    }

    override suspend fun removeMovieFromCart(id: Int) {
        movieDao.removeITemFromChart(id)
    }
}