package com.wily.moviesdbapp.data.source.local.room

import android.provider.ContactsContract
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.wily.moviesdbapp.data.source.local.entity.*

@Dao
interface MoviesDao {

    //movies entity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MoviesEntity>): LongArray

    @Update
    fun updateMovies(movies: MoviesEntity)

    //get data movies
    @WorkerThread
    @Query("SELECT * FROM moviesentities where isMovies = 1")
    fun getMovies(): DataSource.Factory<Int, MoviesEntity>

    //get data tv shows
    @WorkerThread
    @Query("SELECT * FROM moviesentities where isTvshows = 1")
    fun getTvShows(): DataSource.Factory<Int, MoviesEntity>

    //get favourites movies
    @WorkerThread
    @Query("SELECT * FROM moviesentities where isMovies = 1 and isFavorite = 1")
    fun getFavouriteMovie(): DataSource.Factory<Int, MoviesEntity>

    //get favourites tv shows
    @WorkerThread
    @Query("SELECT * FROM moviesentities where isTvshows = 1 and isFavorite = 1")
    fun getFavouriteTvShows(): DataSource.Factory<Int, MoviesEntity>

    //get movies with genres
    @Transaction
    @Query("SELECT * FROM moviesentities WHERE moviesId = :moviesId")
    fun getMoviesWithGenresById(moviesId: Int): LiveData<MoviesWithGenres>

    //get movies director
    @Transaction
    @Query("SELECT * FROM moviesentities WHERE moviesId = :moviesId")
    fun getMoviesWithDirectorById(moviesId: Int): LiveData<MoviesWithDirector>

    //get createby tv shows
    @Transaction
    @Query("SELECT * FROM moviesentities WHERE moviesId = :moviesId")
    fun getTvShowsWithCreatedById(moviesId: Int): LiveData<TvShowsWithCreatedBy>

    //get data movies by title
    @WorkerThread
    @Query("SELECT * FROM moviesentities where title like '%'|| :title ||'%'")
    fun getMoviesByTitle(title: String): DataSource.Factory<Int, MoviesEntity>

    //get data tv shows by name
    @WorkerThread
    @Query("SELECT * FROM moviesentities where name like '%'|| :name ||'%'")
    fun getTvShowsByName(name: String): DataSource.Factory<Int, MoviesEntity>


    //genres entity
    @Query("SELECT * FROM genresentities")
    fun getGenres(): LiveData<List<GenresEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(genres: List<GenresEntity>)


    //director entity
    @Query("SELECT * FROM directorentities")
    fun getDirector(): LiveData<List<DirectorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDirector(director: List<DirectorEntity>)


    //createdby entity
    @Query("SELECT * FROM createdbyentities")
    fun getCreatedBy(): LiveData<List<CreatedByEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCreatedBy(director: List<CreatedByEntity>)

    //sorty query
    @RawQuery(observedEntities = [MoviesEntity::class])
    fun getAllMoviesSort(query: SupportSQLiteQuery): DataSource.Factory<Int, MoviesEntity>
}