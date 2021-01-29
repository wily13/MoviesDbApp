package com.wily.moviesdbapp.api

import com.wily.moviesdbapp.data.source.remote.response.*
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getAllMoviesPage(
        @Query("api_key") api: String
    ): Deferred<MoviesResponse>

    @GET("tv/popular")
    fun getAllTvShowsPage(
        @Query("api_key") api: String
    ): Deferred<MoviesResponse>

    @GET("movie/{movie_id}")
    fun getDetailMoviesPage2(
        @Path("movie_id") id: Int,
        @Query("api_key") api: String
    ): Call<MoviesObject>

    @GET("tv/{tv_id}")
    fun getDetailTvShowsPage(
        @Path("tv_id") id: Int,
        @Query("api_key") api: String
    ): Deferred<MoviesObject>

    @GET("movie/{movie_id}/credits")
    fun getDirectorMovies(
        @Path("movie_id") id: Int,
        @Query("api_key") api: String
    ): Deferred<CreditsResponse>

    @GET("search/movie")
    fun getSearchMovies(
        @Query("api_key") api: String,
        @Query("query") query: String
    ): Deferred<MoviesResponse>

    @GET("search/tv")
    fun getSearchTvShows(
        @Query("api_key") api: String,
        @Query("query") query: String
    ): Deferred<MoviesResponse>
}