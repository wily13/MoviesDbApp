package com.wily.moviesdbapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.wily.moviesdbapp.data.source.MoviesRepository
import com.wily.moviesdbapp.data.source.local.entity.MoviesEntity
import com.wily.moviesdbapp.valueobject.Resource

class TvShowViewModel (private val moviesRepository: MoviesRepository) : ViewModel() {

    fun getAllTvShowsSortBy(sort: String): LiveData<Resource<PagedList<MoviesEntity>>> = moviesRepository.getAllTvShowsPage(sort)
}