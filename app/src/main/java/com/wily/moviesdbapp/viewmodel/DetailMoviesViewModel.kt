package com.wily.moviesdbapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.wily.moviesdbapp.data.source.MoviesRepository
import com.wily.moviesdbapp.data.source.local.entity.MoviesEntity
import com.wily.moviesdbapp.data.source.local.entity.MoviesWithDirector
import com.wily.moviesdbapp.data.source.local.entity.MoviesWithGenres
import com.wily.moviesdbapp.data.source.local.entity.TvShowsWithCreatedBy
import com.wily.moviesdbapp.valueobject.Resource

class DetailMoviesViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    val moviesId  = MutableLiveData<Int>()

    fun setSelectedMovies(moviesId: Int) {
        this.moviesId.value = moviesId
    }

    //movies
    var moviesDetail: LiveData<Resource<MoviesWithGenres>> = Transformations.switchMap(moviesId) { mMoviesId ->
        moviesRepository.getDetailMovies(mMoviesId)
    }

    var moviesDirector: LiveData<Resource<MoviesWithDirector>> = Transformations.switchMap(moviesId) { mMoviesId ->
        moviesRepository.getMoviesDirector(mMoviesId)
    }


    //tv shows
    var tvShowsDetail: LiveData<Resource<MoviesWithGenres>> = Transformations.switchMap(moviesId) { mMoviesId ->
        moviesRepository.getDetailTvShows(mMoviesId)
    }

    var tvShowsCreatedBy: LiveData<Resource<TvShowsWithCreatedBy>> = Transformations.switchMap(moviesId) { mMoviesId ->
        moviesRepository.getMoviesCreatedBy(mMoviesId)
    }


    //favourites
    fun setFavoriteMovie(dataMovie: MoviesEntity, newStatus:Boolean) =
        moviesRepository.setFavoriteMovie(dataMovie, newStatus)
}