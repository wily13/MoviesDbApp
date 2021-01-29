package com.wily.moviesdbapp.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.wily.moviesdbapp.data.source.local.entity.MoviesEntity
import com.wily.moviesdbapp.data.source.local.entity.MoviesWithDirector
import com.wily.moviesdbapp.data.source.local.entity.MoviesWithGenres
import com.wily.moviesdbapp.data.source.local.entity.TvShowsWithCreatedBy
import com.wily.moviesdbapp.valueobject.Resource

interface MoviesDataSource {

    fun getAllMoviesPage(sort: String): LiveData<Resource<PagedList<MoviesEntity>>>

    fun getAllTvShowsPage(sort: String): LiveData<Resource<PagedList<MoviesEntity>>>

    fun getDetailMovies(moviesId: Int): LiveData<Resource<MoviesWithGenres>>

    fun getDetailTvShows(moviesId: Int): LiveData<Resource<MoviesWithGenres>>

    fun getMoviesDirector(moviesId: Int): LiveData<Resource<MoviesWithDirector>>

    fun getMoviesCreatedBy(moviesId: Int): LiveData<Resource<TvShowsWithCreatedBy>>

    fun setFavoriteMovie(course: MoviesEntity, state: Boolean)

    fun getFavoriteMoviesPage(): LiveData<Resource<PagedList<MoviesEntity>>>

    fun getFavoriteTvShowsPage(): LiveData<Resource<PagedList<MoviesEntity>>>

    fun getSearchMoviesPage(query: String): LiveData<Resource<PagedList<MoviesEntity>>>

    fun getSearchTvShowsPage(query: String): LiveData<Resource<PagedList<MoviesEntity>>>

}