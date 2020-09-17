package com.example.movieshop.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(items: List<Movie>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(movie: Movie)

    @Query("UPDATE movies SET quantity = :quantity WHERE id = :id")
    suspend fun updateQuantity(id: Int, quantity: Int)

    @Query("SELECT * FROM movies")
    fun getAllMoviesFlow(): Flow<List<Movie>>

    @Query("SELECT * FROM movies where quantity > 0")
    fun getAllCartMoviesFlow(): Flow<List<Movie>>

    @Query(value = "SELECT COUNT(*) FROM movies ")
    fun countMovies(): Int

    @Query(value = "SELECT * FROM movies WHERE id = :id ")
    suspend fun getMovieById(id: Int): Movie

    @Query(value = "DELETE  FROM movies WHERE quantity > 0")
    suspend fun removeAllItemInCart()
}