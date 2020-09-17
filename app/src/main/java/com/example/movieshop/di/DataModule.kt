package com.example.movieshop.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.movieshop.data.local.LocalDataSource
import com.example.movieshop.data.local.MoviesDatabase
import com.example.movieshop.data.local.RoomDataSource
import com.example.movieshop.data.remote.MoviesApi
import com.example.movieshop.data.remote.NetworkConnectionInterceptor
import com.example.movieshop.data.remote.RemoteDataSource
import com.example.movieshop.data.remote.RetrofitDataSource
import com.example.movieshop.repository.MoviesRepository
import com.example.movieshop.repository.MoviesRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): MoviesDatabase =
        Room.databaseBuilder(app, MoviesDatabase::class.java, "database-name").build()

    @Provides
    @Singleton
    fun provideNetworkConnectionInterceptor(@ApplicationContext context: Context): Interceptor =
        NetworkConnectionInterceptor(context)

    @Provides
    @Singleton
    fun provideApiService(interceptor: Interceptor) = MoviesApi.invoke(interceptor)
    //    ----------------------- Repository----------------------

    @Provides
    @Singleton
    fun provideLocalDataSource(db: MoviesDatabase): LocalDataSource = RoomDataSource(db)

    @Provides
    @Singleton
    fun provideRemoteDataSource(service: MoviesApi): RemoteDataSource =
        RetrofitDataSource(service)

    @Provides
    @Singleton
    fun provideCounterRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): MoviesRepository = MoviesRepositoryImp(localDataSource, remoteDataSource)

}