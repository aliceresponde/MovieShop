package com.example.movieshop.repository

import com.example.movieshop.BuildConfig.IMG_URL
import com.example.movieshop.data.local.LocalDataSource
import com.example.movieshop.data.local.Movie
import com.example.movieshop.data.remote.RemoteDataSource
import com.example.movieshop.data.remote.RemoteResponse
import com.example.movieshop.data.remote.response.MovieResult
import com.example.movieshop.ui.model.MovieItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRepositoryImp(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : MoviesRepository {
    override suspend fun syncData() {
        val result = remote.getMovies()
        if (result is RemoteResponse.Success) {
            val data = result.data.map { it.toMovie() }
            local.insertAll(data)
        }
    }

    override fun getAllMoviesFlow(): Flow<List<MovieItem>> {
        return local.getMoviesFlow().map { movies ->
            movies.map { it.toMovieItem() }
        }
    }

    override fun getMovieDetailFlow(id: Int): Flow<MovieItem> {
        return local.getMovieDetailFlow(id).map { it.toMovieItem() }
    }

    override fun getShoppingMoviesFlow(): Flow<List<MovieItem>> {
        return local.getShoppingMoviesFlow().map { movies ->
            movies.map { it.toMovieItem() }
        }
    }

    override suspend fun removeAllItemInCart() {
        local.removeAllItemInCart()
    }

    override suspend fun addMovieToCart(id: Int) {
        local.addMovieToCar(id)
    }

    override suspend fun removeMovieFromCart(id: Int) {
        local.removeMovieFromCart(id)
    }

    private fun Movie.toMovieItem() =
        MovieItem(
            id = id,
            name = title,
            imageUrl = IMG_URL + imageUrl,
            overview = overview,
            date = date,
            quantity = quantity
        )

    private fun MovieResult.toMovie() =
        Movie(
            id = id,
            title = title,
            overview = overview ?: "",
            date = date ?: "",
            imageUrl = imageUrl ?: "",
            voteAverage = voteAverage
        )
}