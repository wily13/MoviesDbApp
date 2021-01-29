package com.wily.moviesdbapp.di

import android.content.Context
import com.wily.moviesdbapp.data.source.MoviesRepository
import com.wily.moviesdbapp.data.source.local.LocalDataSource
import com.wily.moviesdbapp.data.source.local.room.MoviesDatabase
import com.wily.moviesdbapp.data.source.remote.RemoteDataSource
import com.wily.moviesdbapp.utils.AppExecutors

object Injection {

    fun provideRepository(context: Context): MoviesRepository{
        val database = MoviesDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.moviesDao())
        val appExecutors = AppExecutors()

        return MoviesRepository.getInstance(appExecutors, localDataSource, remoteDataSource)
    }
}