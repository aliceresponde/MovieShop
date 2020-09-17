package com.example.movieshop.di

import com.example.movieshop.repository.MoviesRepository
import com.example.movieshop.ui.movielist.MoviesListViewModel
import com.example.movieshop.ui.shoppingchart.ShoppingChartViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO

@Module
@InstallIn(ActivityRetainedComponent::class)
object MovieListFragmentModule {

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = IO

    @Provides
    fun providesMoviesListViewModel(
        coroutineDispatcher: CoroutineDispatcher,
        repository: MoviesRepository
    ) = MoviesListViewModel(coroutineDispatcher, repository)

    @Provides
    fun providesShoppingChartViewModel(
        coroutineDispatcher: CoroutineDispatcher,
        repository: MoviesRepository
    ) = ShoppingChartViewModel(coroutineDispatcher, repository)
}