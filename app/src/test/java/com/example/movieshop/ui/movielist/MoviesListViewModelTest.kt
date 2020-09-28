package com.example.movieshop.ui.movielist

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.movieshop.repository.MoviesRepository
import com.example.movieshop.ui.model.MovieItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
internal class MoviesListViewModelTest() {
    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

//    @get:Rule
//    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var loadingObserver: Observer<Int>

    @Mock
    lateinit var moviesObserver: Observer<List<MovieItem>>

    @Mock
    private lateinit var repository: MoviesRepository

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var sut: MoviesListViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        sut = MoviesListViewModel(testDispatcher, repository)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

//    @Test
//    fun init() = testDispatcher.runBlockingTest {
//        // given
//        sut.loadingVisibility.observeForever(loadingObserver)
//        sut.movies.observeForever(moviesObserver)
//
//        `when`(repository.getAllMoviesFlow()).thenReturn( flowOf(listOf()))
//
//        // when
//        sut = MoviesListViewModel(testDispatcher, repository)
//
//        // then
//        verify(repository.syncData(), times(1))
//        verify(sut.movies.value != null)
//    }

     @Test
     fun  addItem() = testDispatcher.runBlockingTest{
         /// given
         val id = 123
         sut.loadingVisibility.observeForever(loadingObserver)
         //when
         sut.addItemToCart(id)
         // then
         verify(loadingObserver).onChanged(VISIBLE)
         verify(loadingObserver).onChanged(GONE)
     }
}