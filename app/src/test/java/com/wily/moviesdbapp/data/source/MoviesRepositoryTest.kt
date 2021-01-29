package com.wily.moviesdbapp.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.verify
import com.wily.moviesdbapp.data.source.local.LocalDataSource
import com.wily.moviesdbapp.data.source.local.entity.MoviesEntity
import com.wily.moviesdbapp.data.source.local.entity.MoviesWithDirector
import com.wily.moviesdbapp.data.source.local.entity.MoviesWithGenres
import com.wily.moviesdbapp.data.source.local.entity.TvShowsWithCreatedBy
import com.wily.moviesdbapp.data.source.remote.RemoteDataSource
import com.wily.moviesdbapp.utils.*
import com.wily.moviesdbapp.valueobject.Resource
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito

class MoviesRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)

    private val moviesRepository = FakeMoviesRepository(appExecutors, local, remote)

    private val moviesResponses = MoviesDataDummy.generateRemoteDummyMovies()
    private val moviesId = moviesResponses[0].moviesId
    private val tvShowsResponses = MoviesDataDummy.generateRemoteDummyTvShows()
    private val tvShowsId = tvShowsResponses[0].moviesId
    private val moviesWithGenresResponse = MoviesDataDummy.generateRemoteDummyGenres(moviesId)
    private val moviesWithDirectorResponse = MoviesDataDummy.generateRemoteDummyDirector(moviesId)
    private val tvshowsWithGenresResponse = MoviesDataDummy.generateRemoteDummyGenres(tvShowsId)
    private val tvshowsWithCreatedResponse = MoviesDataDummy.generateRemoteDummyCreatedBy(tvShowsId)
    private val dummyQueryMovies = "The Croods: A New Age"
    private val dummyQueryTvShows = "The Mandalorian"


    @Test
    fun getAllMoviesPage() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesEntity>
        Mockito.`when`(local.getAllMoviesSortedAsPaged(SortUtils.DEFAULT)).thenReturn(dataSourceFactory)
        moviesRepository.getAllMoviesPage(SortUtils.DEFAULT)

        val moviesEntities = Resource.success(PagedListUtil.mockPagedList(MoviesDataDummy.generateDummyMovies()))
        verify(local).getAllMoviesSortedAsPaged(SortUtils.DEFAULT)
        assertNotNull(moviesEntities.data)
    }

    @Test
    fun getAllTvShowsPage() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesEntity>
        Mockito.`when`(local.getAllTvShowsSortedAsPaged(SortUtils.DEFAULT)).thenReturn(dataSourceFactory)
        moviesRepository.getAllTvShowsPage(SortUtils.DEFAULT)

        val tvShowsEntities = Resource.success(PagedListUtil.mockPagedList(MoviesDataDummy.generateDummyTvShows()))
        verify(local).getAllTvShowsSortedAsPaged(SortUtils.DEFAULT)
        assertNotNull(tvShowsEntities.data)
    }

    @Test
    fun getDetailMovies() {
        val dummyEntity = MutableLiveData<MoviesWithGenres>()
        dummyEntity.value = MoviesDataDummy.generateDummyMoviesWithGenres(MoviesDataDummy.generateDummyMovies()[0], true)
        Mockito.`when`<LiveData<MoviesWithGenres>>(local.getMovieseWithGenres(moviesId)).thenReturn(dummyEntity)

        val moviesEntitiesContent = LiveDataTestUtil.getValue(moviesRepository.getDetailMovies(moviesId))
        verify(local).getMovieseWithGenres(moviesId)
        assertNotNull(moviesEntitiesContent)
        assertNotNull(moviesEntitiesContent.data)
        assertNotNull(moviesEntitiesContent.data?.mMovies)
        assertEquals(moviesResponses[0].title, moviesEntitiesContent.data?.mMovies?.title)
        assertNotNull(moviesEntitiesContent.data?.mGenres)
        assertEquals(moviesWithGenresResponse[0].name, moviesEntitiesContent.data?.mGenres?.get(0)?.name)
    }

    @Test
    fun getDetailTvShows() {
        val dummyEntity = MutableLiveData<MoviesWithGenres>()
        dummyEntity.value = MoviesDataDummy.generateDummyMoviesWithGenres(MoviesDataDummy.generateDummyTvShows()[0], true)
        Mockito.`when`<LiveData<MoviesWithGenres>>(local.getMovieseWithGenres(tvShowsId)).thenReturn(dummyEntity)

        val tvShowsEntitiesContent = LiveDataTestUtil.getValue(moviesRepository.getDetailTvShows(tvShowsId))
        verify(local).getMovieseWithGenres(tvShowsId)
        assertNotNull(tvShowsEntitiesContent)
        assertNotNull(tvShowsEntitiesContent.data)
        assertNotNull(tvShowsEntitiesContent.data?.mMovies)
        assertEquals(tvShowsResponses[0].name, tvShowsEntitiesContent.data?.mMovies?.name)
        assertNotNull(tvShowsEntitiesContent.data?.mGenres)
        assertEquals(tvshowsWithGenresResponse[0].name, tvShowsEntitiesContent.data?.mGenres?.get(0)?.name)
    }

    @Test
    fun getDirectorMovies() {
        val dummyEntity = MutableLiveData<MoviesWithDirector>()
        dummyEntity.value = MoviesDataDummy.generateDummyMoviesWithDirector(MoviesDataDummy.generateDummyMovies()[0], true)
        Mockito.`when`<LiveData<MoviesWithDirector>>(local.getMovieseWithDirector(moviesId)).thenReturn(dummyEntity)

        val moviesEntitiesContent = LiveDataTestUtil.getValue(moviesRepository.getMoviesDirector(moviesId))
        verify(local).getMovieseWithDirector(moviesId)
        assertNotNull(moviesEntitiesContent)
        assertNotNull(moviesEntitiesContent.data)
        assertNotNull(moviesEntitiesContent.data?.mMovies)
        assertEquals(moviesResponses[0].title, moviesEntitiesContent.data?.mMovies?.title)
        assertNotNull(moviesEntitiesContent.data?.mDirector)
        assertEquals(moviesWithDirectorResponse[0].name, moviesEntitiesContent.data?.mDirector?.get(0)?.name)
    }

    @Test
    fun getCreatedByMovies() {
        val dummyEntity = MutableLiveData<TvShowsWithCreatedBy>()
        dummyEntity.value = MoviesDataDummy.generateDummyTvShowsWithCreatedBy(MoviesDataDummy.generateDummyTvShows()[0], true)
        Mockito.`when`<LiveData<TvShowsWithCreatedBy>>(local.getTvShowsWithCreatedBy(moviesId)).thenReturn(dummyEntity)

        val moviesEntitiesContent = LiveDataTestUtil.getValue(moviesRepository.getMoviesCreatedBy(moviesId))
        verify(local).getTvShowsWithCreatedBy(moviesId)
        assertNotNull(moviesEntitiesContent)
        assertNotNull(moviesEntitiesContent.data)
        assertNotNull(moviesEntitiesContent.data?.mMovies)
        assertEquals(tvShowsResponses[0].name, moviesEntitiesContent.data?.mMovies?.name)
        assertNotNull(moviesEntitiesContent.data?.mCreatedBy)
        assertEquals(tvshowsWithCreatedResponse[0].name, moviesEntitiesContent.data?.mCreatedBy?.get(0)?.name)
    }

    @Test
    fun getFavoriteMoviesPage() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesEntity>
        Mockito.`when`(local.getFavoriteMoviesAsPaged()).thenReturn(dataSourceFactory)
        moviesRepository.getFavoriteMoviesPage()

        val moviesEntities = Resource.success(PagedListUtil.mockPagedList(MoviesDataDummy.generateDummyMovies()))
        verify(local).getFavoriteMoviesAsPaged()
        assertNotNull(moviesEntities)
        assertEquals(moviesResponses.size.toLong(), moviesEntities.data?.size?.toLong())
    }

    @Test
    fun getFavoriteTvShowsPage() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesEntity>
        Mockito.`when`(local.getFavoriteTvShowsAsPaged()).thenReturn(dataSourceFactory)
        moviesRepository.getFavoriteTvShowsPage()

        val tvshowssEntities = Resource.success(PagedListUtil.mockPagedList(MoviesDataDummy.generateDummyTvShows()))
        verify(local).getFavoriteTvShowsAsPaged()
        assertNotNull(tvshowssEntities)
        assertEquals(tvShowsResponses.size.toLong(), tvshowssEntities.data?.size?.toLong())
    }

    @Test
    fun getSearchMoviesPage() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesEntity>
        Mockito.`when`(local.getSearchMoviesByTitle(dummyQueryMovies)).thenReturn(dataSourceFactory)
        moviesRepository.getSearchMoviesPage(dummyQueryMovies)

        val moviesEntities = Resource.success(PagedListUtil.mockPagedList(MoviesDataDummy.generateDummyMovies()))
        verify(local).getSearchMoviesByTitle(dummyQueryMovies)
        assertNotNull(moviesEntities)
        assertEquals(moviesResponses.get(0).title, moviesEntities.data?.get(0)?.title)
    }

    @Test
    fun getSearchTvShowsPage() {
        val dataSourceFactory = Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesEntity>
        Mockito.`when`(local.getSearchTvShowsByName(dummyQueryTvShows)).thenReturn(dataSourceFactory)
        moviesRepository.getSearchTvShowsPage(dummyQueryTvShows)

        val tvshowsEntities = Resource.success(PagedListUtil.mockPagedList(MoviesDataDummy.generateDummyTvShows()))
        verify(local).getSearchTvShowsByName(dummyQueryTvShows)
        assertNotNull(tvshowsEntities)
        assertEquals(tvShowsResponses.get(0).name, tvshowsEntities.data?.get(0)?.name)
    }
}