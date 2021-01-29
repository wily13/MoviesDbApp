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
class TvShowViewModelTest {

    private lateinit var viewModel: TvShowViewModel

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
        viewModel = TvShowViewModel(moviesRepository)
    }

    @Test
    fun getAllTvShowsSortBy() {
        val dummyMovies = Resource.success(pagedList)
        val tvShows = MutableLiveData<Resource<PagedList<MoviesEntity>>>()
        tvShows.value = dummyMovies

        Mockito.`when`(moviesRepository.getAllTvShowsPage(SortUtils.POPULAR)).thenReturn(tvShows)
        val tvShowsEntities = viewModel.getAllTvShowsSortBy(SortUtils.POPULAR).value?.data
        Mockito.verify(moviesRepository).getAllTvShowsPage(SortUtils.POPULAR)
        assertNotNull(tvShowsEntities)

        viewModel.getAllTvShowsSortBy(SortUtils.POPULAR).observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovies)
    }
}