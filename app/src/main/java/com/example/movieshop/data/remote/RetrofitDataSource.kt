package com.example.movieshop.data.remote

import com.example.movieshop.data.remote.response.MovieResult
import javax.inject.Inject

class RetrofitDataSource @Inject constructor(private val api: MoviesApi) : RemoteDataSource {
    override suspend fun getMovies(): RemoteResponse {
        return try {
            val data = api.getPopularMovies().movies
            RemoteResponse.Success(data = data)
        } catch (e: Throwable) {
            RemoteResponse.Error
        }
    }
}

sealed class RemoteResponse {
    class Success(val data: List<MovieResult>) : RemoteResponse()
    object Error : RemoteResponse()
}