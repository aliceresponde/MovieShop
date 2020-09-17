package com.example.movieshop.data.remote

import com.example.movieshop.data.remote.response.MovieResult

interface RemoteDataSource {
    suspend fun getMovies(): RemoteResponse
}
