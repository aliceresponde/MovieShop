package com.example.movieshop.repository

import com.example.movieshop.BuildConfig
import com.example.movieshop.data.local.LocalDataSource
import com.example.movieshop.data.local.Movie
import com.example.movieshop.data.remote.RemoteDataSource
import com.example.movieshop.data.remote.RemoteResponse
import com.example.movieshop.data.remote.response.MovieResult
import com.example.movieshop.ui.model.MovieItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class MoviesRepositoryImpTest {

    @Mock
    private lateinit var localDataSource: LocalDataSource

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    lateinit var sut: MoviesRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        sut = MoviesRepositoryImp(localDataSource, remoteDataSource)
    }

    @Test
    fun `sync data with success server request`() {
        runBlockingTest {
            // given
            `when`(remoteDataSource.getMovies())
                .thenReturn(RemoteResponse.Success(listOf(MOVIE_RESULT)))
            // when
            sut.syncData()
            // then
            verify(localDataSource).insertAll(listOf(MOVIE))
        }
    }

    @Test
    fun `sync data with error on server request`() {
        runBlockingTest {
            //given
            `when`(remoteDataSource.getMovies()).thenReturn(REMOTE_RESPONSE_ERROR)
            // when
            sut.syncData()
            verifyNoInteractions(localDataSource)
        }
    }

    @Test
    fun `getAllMoviesFlow returns a flow with empty data list`() {
        // given
        `when`(localDataSource.getMoviesFlow()).thenReturn(FAKE_EMPTY_MOVIE_FLOW)
        // when
        sut.getAllMoviesFlow()
        // then
        verify(localDataSource, times(1)).getMoviesFlow()
    }

    @Test
    fun `getAllMoviesFlow returns a flow with  data list`() {
        // given
        `when`(localDataSource.getMoviesFlow()).thenReturn(FAKE_MOVIE_LIST_FLOW)
        // when
        sut.getAllMoviesFlow()
        // then
        verify(localDataSource, times(1)).getMoviesFlow()
    }

    @Test
    fun `getMovieDetailFlow localDataSource`(){
        // given
        val id = 1
        `when`(localDataSource.getMovieDetailFlow(id)).thenReturn(FAKE_MOVIE_FLOW)
        // when
        val result = sut.getMovieDetailFlow(id)
        //the
        verify(localDataSource, times(1)).getMovieDetailFlow(id)
        assertTrue(result is Flow<MovieItem>)
    }

    @Test
    fun `getShoppingMoviesFlow empty data flow`(){
        // given
        `when`(localDataSource.getShoppingMoviesFlow()).thenReturn(FAKE_EMPTY_MOVIE_FLOW)
        // when
        val result =sut.getShoppingMoviesFlow()
        verify(localDataSource, times(1)).getShoppingMoviesFlow()
        assertTrue(result is Flow<List<MovieItem>>)
    }

    @Test
    fun `getShoppingMoviesFlow with data flow`(){
        // given
        `when`(localDataSource.getShoppingMoviesFlow()).thenReturn(FAKE_MOVIE_LIST_FLOW)
        // when
        val result =sut.getShoppingMoviesFlow()
        verify(localDataSource, times(1)).getShoppingMoviesFlow()
        assertTrue(result is Flow<List<MovieItem>>)
    }


    @Test
    fun `removeAllItemInCart call local datasource removeAllItemInCart`()= runBlockingTest{
        // when
        sut.removeAllItemInCart()
        verify(localDataSource, times(1)).removeAllItemInCart()
    }

    @Test
    fun `addMovieToCart call local data source addMovieToCart with same id`() = runBlockingTest{
        //given
        val id = 1
        //when
        sut.addMovieToCart(id)
        //then
        verify(localDataSource, times(1)).addMovieToCar(id)
    }

    @Test
    fun `removeMovieFromCart call local data source removeMovieFromCart with same id`() = runBlockingTest{
        //given
        val id = 1
        //when
        sut.removeMovieFromCart(id)
        //then
        verify(localDataSource, times(1)).removeMovieFromCart(id)
    }


    companion object {
        val MOVIE = Movie(
            id = 1,
            title = "title",
            overview = "overview",
            date = "2020-02-11",
            imageUrl = "url",
            voteAverage = 2f,
            quantity = 0
        )

        private val MOVIE_ITEM = MovieItem(
            id = 1,
            name = "title",
            imageUrl = BuildConfig.IMG_URL + "url",
            overview = "overview",
            date = "2020-02-11",
            quantity = 0
        )


        private val EMPTY_MOVIE_LIST = listOf<Movie>()
        private val MOVIE_LIST = listOf(MOVIE)

        private val REMOTE_RESPONSE_ERROR = RemoteResponse.Error
        private val MOVIE_RESULT = MovieResult(1, "title", "overview", "2020-02-11", "url", 2f, 3f)

        private val FAKE_EMPTY_MOVIE_FLOW: Flow<List<Movie>> = flow { EMPTY_MOVIE_LIST }
        private val FAKE_MOVIE_LIST_FLOW: Flow<List<Movie>> = flow { MOVIE_LIST }
        private val FAKE_MOVIE_FLOW : Flow<Movie> = flow { MOVIE }
        private val FAKE_MOVIE_ITEM_FLOW : Flow<Movie> = flow { MOVIE_ITEM }
    }
}