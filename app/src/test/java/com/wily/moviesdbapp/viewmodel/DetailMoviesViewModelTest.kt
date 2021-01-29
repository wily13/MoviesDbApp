package com.wily.moviesdbapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.wily.moviesdbapp.data.source.MoviesRepository
import com.wily.moviesdbapp.data.source.local.entity.MoviesWithDirector
import com.wily.moviesdbapp.data.source.local.entity.MoviesWithGenres
import com.wily.moviesdbapp.data.source.local.entity.TvShowsWithCreatedBy
import com.wily.moviesdbapp.utils.MoviesDataDummy
import com.wily.moviesdbapp.valueobject.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMoviesViewModelTest {

    private lateinit var viewModel: DetailMoviesViewModel
    private val dummyMovies = MoviesDataDummy.generateDummyMovies()[0]
    private val dummymoviesId: Int = dummyMovies.moviesId
    private val dummyTvShows = MoviesDataDummy.generateDummyTvShows()[0]
    private val dummyTvShowsId: Int = dummyTvShows.moviesId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Mock
    private lateinit var observerMoviesGenres: Observer<Resource<MoviesWithGenres>>

    @Mock
    private lateinit var observerMoviesDirector: Observer<Resource<MoviesWithDirector>>

    @Mock
    private lateinit var observerTvShowsCreated: Observer<Resource<TvShowsWithCreatedBy>>


    @Before
    fun setUp() {
        viewModel = DetailMoviesViewModel(moviesRepository)
    }

    // test movies
    @Test
    fun getDetailMovies(){
        val dummyMoviesWithGenres = Resource.success(MoviesDataDummy.generateDummyMoviesWithGenres(dummyMovies, true))
        val movies = MutableLiveData<Resource<MoviesWithGenres>>()
        movies.value = dummyMoviesWithGenres
        viewModel.setSelectedMovies(dummymoviesId)
        Mockito.`when`(moviesRepository.getDetailMovies(dummymoviesId)).thenReturn(movies)

        viewModel.moviesDetail.observeForever(observerMoviesGenres)
        Mockito.verify(observerMoviesGenres).onChanged(dummyMoviesWithGenres)
    }

    @Test
    fun getDirectorMovies(){
        val dummyMoviesWithDirector = Resource.success(MoviesDataDummy.generateDummyMoviesWithDirector(dummyMovies, true))
        val movies = MutableLiveData<Resource<MoviesWithDirector>>()
        movies.value = dummyMoviesWithDirector
        viewModel.setSelectedMovies(dummymoviesId)
        Mockito.`when`(moviesRepository.getMoviesDirector(dummymoviesId)).thenReturn(movies)

        viewModel.moviesDirector.observeForever(observerMoviesDirector)
        Mockito.verify(observerMoviesDirector).onChanged(dummyMoviesWithDirector)
    }


    // test tv shows
    @Test
    fun getDetailTvShows(){
        val dummyTvShowsWithGenres = Resource.success(MoviesDataDummy.generateDummyTvShowsWithGenres(dummyTvShows, true))
        val tvShows = MutableLiveData<Resource<MoviesWithGenres>>()
        tvShows.value = dummyTvShowsWithGenres
        viewModel.setSelectedMovies(dummyTvShowsId)
        Mockito.`when`(moviesRepository.getDetailTvShows(dummyTvShowsId)).thenReturn(tvShows)

        viewModel.tvShowsDetail.observeForever(observerMoviesGenres)
        Mockito.verify(observerMoviesGenres).onChanged(dummyTvShowsWithGenres)
    }

    @Test
    fun getCreatedByMovies(){
        val dummyTvShowsWithCreatedBy = Resource.success(MoviesDataDummy.generateDummyTvShowsWithCreatedBy(dummyTvShows, true))
        val tvShows = MutableLiveData<Resource<TvShowsWithCreatedBy>>()
        tvShows.value = dummyTvShowsWithCreatedBy
        viewModel.setSelectedMovies(dummyTvShowsId)
        Mockito.`when`(moviesRepository.getMoviesCreatedBy(dummyTvShowsId)).thenReturn(tvShows)

        viewModel.tvShowsCreatedBy.observeForever(observerTvShowsCreated)
        Mockito.verify(observerTvShowsCreated).onChanged(dummyTvShowsWithCreatedBy)
    }
}