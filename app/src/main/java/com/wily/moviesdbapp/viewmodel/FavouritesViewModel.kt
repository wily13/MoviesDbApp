package com.wily.moviesdbapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.wily.moviesdbapp.data.source.MoviesRepository
import com.wily.moviesdbapp.data.source.local.entity.MoviesEntity
import com.wily.moviesdbapp.valueobject.Resource

class FavouritesViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    fun setFavouritesMovies(moviesEntity: MoviesEntity) {
        val newState = !moviesEntity.isFavorite
        moviesRepository.setFavoriteMovie(moviesEntity, newState)
    }

    fun getFavoritesMovies(): LiveData<Resource<PagedList<MoviesEntity>>> = moviesRepository.getFavoriteMoviesPage()

    fun getFavoritesTvShows(): LiveData<Resource<PagedList<MoviesEntity>>> = moviesRepository.getFavoriteTvShowsPage()


}