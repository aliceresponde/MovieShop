package com.example.movieshop.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movies")
data class Movie(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    @ColumnInfo(name = "release_date")
    val date: String,
    @ColumnInfo(name = "poster_path")
    val imageUrl: String? ="",
    @ColumnInfo(name = "vote_average")
    val voteAverage: Float,
    val quantity: Int = 0
)