package com.example.movieshop.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun getMoviesDao(): MovieDao

    companion object {
        private const val DATABASE_NAME = "movieboard.db"

        operator fun invoke(context: Context): MoviesDatabase {
            return Room.databaseBuilder(context, MoviesDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}