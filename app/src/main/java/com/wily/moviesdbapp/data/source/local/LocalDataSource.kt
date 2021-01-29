package com.wily.moviesdbapp.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.wily.moviesdbapp.data.source.local.entity.*
import com.wily.moviesdbapp.data.source.local.room.MoviesDao
import com.wily.moviesdbapp.utils.SortUtils

class LocalDataSource private constructor(private val mMoviesDao: MoviesDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(moviesDao: MoviesDao): LocalDataSource {
            if (INSTANCE == null) {
                INSTANCE = LocalDataSource(moviesDao)
            }
            return INSTANCE as LocalDataSource
        }
    }

    // movies
    fun getAllMoviesSortedAsPaged(sort: String): DataSource.Factory<Int, MoviesEntity> {
        val query = SortUtils.getMoviesSortedQuery(sort)
        return mMoviesDao.getAllMoviesSort(query)
    }

    // tv shows
    fun getAllTvShowsSortedAsPaged(sort: String): DataSource.Factory<Int, MoviesEntity> {
        val query = SortUtils.getTvShowsSortedQuery(sort)
        return mMoviesDao.getAllMoviesSort(query)
    }

    // movies and tv shows
    fun getMovieseWithGenres(moviesId: Int): LiveData<MoviesWithGenres> =
        mMoviesDao.getMoviesWithGenresById(moviesId)

    // movies director
    fun getMovieseWithDirector(moviesId: Int): LiveData<MoviesWithDirector> =
        mMoviesDao.getMoviesWithDirectorById(moviesId)

    // creator tv shows
    fun getTvShowsWithCreatedBy(moviesId: Int): LiveData<TvShowsWithCreatedBy> =
        mMoviesDao.getTvShowsWithCreatedById(moviesId)

    fun getSearchMoviesByTitle(title: String): DataSource.Factory<Int, MoviesEntity> =
        mMoviesDao.getMoviesByTitle(title)

    fun getSearchTvShowsByName(title: String): DataSource.Factory<Int, MoviesEntity> =
        mMoviesDao.getTvShowsByName(title)


    fun insertMovie(movies: List<MoviesEntity>) {
        mMoviesDao.insertMovies(movies)
    }

    fun updateMovie(movie: MoviesEntity) {
        mMoviesDao.updateMovies(movie)
    }

    // favorites
    fun setFavoritesMovie(movie: MoviesEntity, state: Boolean) {
        movie.isFavorite = state
        mMoviesDao.updateMovies(movie)
    }

    fun getFavoriteMoviesAsPaged(): DataSource.Factory<Int, MoviesEntity> =
        mMoviesDao.getFavouriteMovie()

    fun getFavoriteTvShowsAsPaged(): DataSource.Factory<Int, MoviesEntity> =
        mMoviesDao.getFavouriteTvShows()

    // genres
    fun insertGenresMovies(genres: List<GenresEntity>) = mMoviesDao.insertGenres(genres)

    // director
    fun insertDirectorMovies(director: List<DirectorEntity>) = mMoviesDao.insertDirector(director)

    // creator
    fun insertCreatedTvShows(createdby: List<CreatedByEntity>) =
        mMoviesDao.insertCreatedBy(createdby)

    // language
    fun setLanguageMovie(movie: MoviesEntity, language: String?) {
        movie.language = language
        mMoviesDao.updateMovies(movie)
    }

}

