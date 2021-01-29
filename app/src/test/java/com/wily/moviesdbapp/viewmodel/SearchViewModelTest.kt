package com.wily.moviesdbapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.wily.moviesdbapp.data.source.MoviesRepository
import com.wily.moviesdbapp.data.source.local.entity.MoviesEntity
import com.wily.moviesdbapp.utils.SortUtils
import com.wily.moviesdbapp.valueobject.Resource
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private val dummyQueryMovies: String = "Wonder Woman 1984"
    private val dummyQueryTvShows: String = "The Mandalorian"

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MoviesEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<MoviesEntity>

    @Before
    fun setUp() {
        viewModel = SearchViewModel(moviesRepository)
    }

    @Test
    fun getSearchMovies() {
        val dummyMovies = Resource.success(pagedList)
        val movies = MutableLiveData<Resource<PagedList<MoviesEntity>>>()
        movies.value = dummyMovies

        Mockito.`when`(moviesRepository.getSearchMoviesPage(dummyQueryMovies)).thenReturn(movies)
        val moviesEntities = viewModel.getSearchMovies(dummyQueryMovies).value?.data
        Mockito.verify(moviesRepository).getSearchMoviesPage(dummyQueryMovies)
        assertNotNull(moviesEntities)

        viewModel.getSearchMovies(dummyQueryMovies).observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovies)
    }

    @Test
    fun getSearchTvShows() {
        val dummyMovies = Resource.success(pagedList)
        val tvShows = MutableLiveData<Resource<PagedList<MoviesEntity>>>()
        tvShows.value = dummyMovies

        Mockito.`when`(moviesRepository.getSearchMoviesPage(dummyQueryTvShows)).thenReturn(tvShows)
        val tvShowsEntities = viewModel.getSearchMovies(dummyQueryTvShows).value?.data
        Mockito.verify(moviesRepository).getSearchMoviesPage(dummyQueryTvShows)
        assertNotNull(tvShowsEntities)

        viewModel.getSearchMovies(dummyQueryTvShows).observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovies)
    }
}