package com.example.movieshop.data.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResult(
    val id: Int,
    @SerializedName("title")
    val title: String = "",
    val overview: String? = "",
    @SerializedName("release_date")
    val date: String? = "",
    @SerializedName("poster_path")
    val imageUrl: String? = "",
    @SerializedName("vote_average")
    val voteAverage: Float,
    val popularity: Float
)
