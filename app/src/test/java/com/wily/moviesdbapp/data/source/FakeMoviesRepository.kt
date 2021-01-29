package com.wily.moviesdbapp.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.wily.moviesdbapp.data.source.local.LocalDataSource
import com.wily.moviesdbapp.data.source.local.entity.*
import com.wily.moviesdbapp.data.source.remote.ApiResponse
import com.wily.moviesdbapp.data.source.remote.RemoteDataSource
import com.wily.moviesdbapp.data.source.remote.response.CreditsResponse
import com.wily.moviesdbapp.data.source.remote.response.MoviesObject
import com.wily.moviesdbapp.utils.AppExecutors
import com.wily.moviesdbapp.valueobject.Resource

class FakeMoviesRepository constructor(
    var appExecutors: AppExecutors,
    var localDataSource: LocalDataSource,
    var remoteDataSource: RemoteDataSource
) : MoviesDataSource {

    // load all movies page
    override fun getAllMoviesPage(sort: String): LiveData<Resource<PagedList<MoviesEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MoviesEntity>, List<MoviesObject>>(appExecutors) {

            public override fun loadFromDB(): LiveData<PagedList<MoviesEntity>> =
                LivePagedListBuilder(localDataSource.getAllMoviesSortedAsPaged(sort), 10).build()

            override fun shouldFetch(data: PagedList<MoviesEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<MoviesObject>>> =
                remoteDataSource.getMoviesAsLiveData()

            override fun saveCallResult(data: List<MoviesObject>) {
                val moviesList = ArrayList<MoviesEntity>()
                for (dataResult in data) {
                    val movies = MoviesEntity(
                        moviesId = dataResult.moviesId,
                        title = dataResult.title,
                        posterPath = dataResult.posterPath,
                        overview = dataResult.overview,
                        originalLanguage = dataResult.originalLanguage,
                        releaseDate = dataResult.releaseDate,
                        popularity = dataResult.popularity,
                        voteAverage = dataResult.voteAverage,
                        voteCount = dataResult.voteCount,
                        budget = dataResult.budget,
                        revenue = dataResult.revenue,
                        tagline = dataResult.tagline,
                        status = dataResult.status,
                        name = dataResult.name,
                        firstAirDate = dataResult.firstAirDate,
                        lastAirDate = dataResult.lastAirDate,
                        type = dataResult.type,
                        isFavorite = false,
                        isMovies = true,
                        isTvshows = false
                    )
                    moviesList.add(movies)
                }
                localDataSource.insertMovie(moviesList)
            }
        }.asLiveData()
    }


    // load all tv shows page
    override fun getAllTvShowsPage(sort: String): LiveData<Resource<PagedList<MoviesEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MoviesEntity>, List<MoviesObject>>(appExecutors) {

            public override fun loadFromDB(): LiveData<PagedList<MoviesEntity>> =
                LivePagedListBuilder(localDataSource.getAllTvShowsSortedAsPaged(sort), 10).build()

            override fun shouldFetch(data: PagedList<MoviesEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<MoviesObject>>> =
                remoteDataSource.getTvShowsAsLiveData()

            override fun saveCallResult(data: List<MoviesObject>) {
                val moviesList = ArrayList<MoviesEntity>()
                for (dataResult in data) {
                    val movies = MoviesEntity(
                        moviesId = dataResult.moviesId,
                        title = dataResult.title,
                        posterPath = dataResult.posterPath,
                        overview = dataResult.overview,
                        originalLanguage = dataResult.originalLanguage,
                        releaseDate = dataResult.releaseDate,
                        popularity = dataResult.popularity,
                        voteAverage = dataResult.voteAverage,
                        voteCount = dataResult.voteCount,
                        budget = dataResult.budget,
                        revenue = dataResult.revenue,
                        tagline = dataResult.tagline,
                        status = dataResult.status,
                        name = dataResult.name,
                        firstAirDate = dataResult.firstAirDate,
                        lastAirDate = dataResult.lastAirDate,
                        type = dataResult.type,
                        isFavorite = false,
                        isMovies = false,
                        isTvshows = true
                    )
                    moviesList.add(movies)
                }
                localDataSource.insertMovie(moviesList)
            }
        }.asLiveData()
    }

    // load detail movies
    override fun getDetailMovies(moviesId: Int): LiveData<Resource<MoviesWithGenres>> {
        return object : NetworkBoundResource<MoviesWithGenres, MoviesObject>(appExecutors) {
            override fun loadFromDB(): LiveData<MoviesWithGenres> =
                localDataSource.getMovieseWithGenres(moviesId)

            override fun shouldFetch(data: MoviesWithGenres?): Boolean =
                data?.mGenres == null || data.mGenres.isEmpty()

            override fun createCall(): LiveData<ApiResponse<MoviesObject>> =
                remoteDataSource.getDetailMoviesAsLiveData(moviesId)

            override fun saveCallResult(data: MoviesObject) {
                val movies = MoviesEntity(
                    moviesId = data.moviesId,
                    title = data.title,
                    posterPath = data.posterPath,
                    overview = data.overview,
                    originalLanguage = data.originalLanguage,
                    releaseDate = data.releaseDate,
                    popularity = data.popularity,
                    voteAverage = data.voteAverage,
                    voteCount = data.voteCount,
                    budget = data.budget,
                    revenue = data.revenue,
                    tagline = data.tagline,
                    status = data.status,
                    name = data.name,
                    firstAirDate = data.firstAirDate,
                    lastAirDate = data.lastAirDate,
                    type = data.type,
                    isFavorite = false,
                    isMovies = true,
                    isTvshows = false
                )
                localDataSource.updateMovie(movies)

                // for insert data genres movies
                val listGenres = data.genres
                val genresList = ArrayList<GenresEntity>()
                if (listGenres != null) {
                    for (dataGenres in listGenres) {
                        val genres = GenresEntity(
                            genreId = dataGenres.id,
                            moviesId = data.moviesId,
                            name = dataGenres.name
                        )
                        genresList.add(genres)
                    }
                    localDataSource.insertGenresMovies(genresList)
                }

                //set language
                val language = if (data.spoken_languages.get(0).englishName.isEmpty()) data.originalLanguage else data.spoken_languages.get(0).englishName
                localDataSource.setLanguageMovie(movies,language)

            }
        }.asLiveData()
    }

    // load detail tv shows
    override fun getDetailTvShows(moviesId: Int): LiveData<Resource<MoviesWithGenres>> {
        return object : NetworkBoundResource<MoviesWithGenres, MoviesObject>(appExecutors) {
            override fun loadFromDB(): LiveData<MoviesWithGenres> =
                localDataSource.getMovieseWithGenres(moviesId)

            override fun shouldFetch(data: MoviesWithGenres?): Boolean =
                data?.mGenres == null || data.mGenres.isEmpty()

            override fun createCall(): LiveData<ApiResponse<MoviesObject>> =
                remoteDataSource.getDetailTvShowsAsLiveData(moviesId)

            override fun saveCallResult(data: MoviesObject) {
                val movies = MoviesEntity(
                    moviesId = data.moviesId,
                    title = data.title,
                    posterPath = data.posterPath,
                    overview = data.overview,
                    originalLanguage = data.originalLanguage,
                    releaseDate = data.releaseDate,
                    popularity = data.popularity,
                    voteAverage = data.voteAverage,
                    voteCount = data.voteCount,
                    budget = data.budget,
                    revenue = data.revenue,
                    tagline = data.tagline,
                    status = data.status,
                    name = data.name,
                    firstAirDate = data.firstAirDate,
                    lastAirDate = data.lastAirDate,
                    type = data.type,
                    isFavorite = false,
                    isMovies = false,
                    isTvshows = true
                )
                localDataSource.updateMovie(movies)

                // for insert data genres movies
                val listGenres = data.genres
                val genresList = ArrayList<GenresEntity>()
                if (listGenres != null) {
                    for (dataGenres in listGenres) {
                        val genres = GenresEntity(
                            genreId = dataGenres.id,
                            moviesId = data.moviesId,
                            name = dataGenres.name
                        )
                        genresList.add(genres)
                    }
                    localDataSource.insertGenresMovies(genresList)
                }

                //set language
                val language = if (data.spoken_languages.get(0).englishName.isEmpty()) data.originalLanguage else data.spoken_languages.get(0).englishName
                localDataSource.setLanguageMovie(movies,language)

            }
        }.asLiveData()
    }

    // get director for movies
    override fun getMoviesDirector(moviesId: Int): LiveData<Resource<MoviesWithDirector>> {
        return object : NetworkBoundResource<MoviesWithDirector, CreditsResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MoviesWithDirector> =
                localDataSource.getMovieseWithDirector(moviesId)

            override fun shouldFetch(data: MoviesWithDirector?): Boolean =
                data?.mDirector == null || data.mDirector.isEmpty()

            override fun createCall(): LiveData<ApiResponse<CreditsResponse>> =
                remoteDataSource.getDirectorMoviesAsLiveData(moviesId)

            override fun saveCallResult(data: CreditsResponse) {
                val directorList = ArrayList<DirectorEntity>()
                for (crewResponse in data.crew) {
                    if (crewResponse.job == "Director") {
                        val director = DirectorEntity(
                            directorId = crewResponse.id,
                            moviesId = moviesId,
                            name = crewResponse.name,
                            job = crewResponse.job
                        )
                        directorList.add(director)
                    }
                }
                localDataSource.insertDirectorMovies(directorList)

            }
        }.asLiveData()
    }

    // get created by for tv shows
    override fun getMoviesCreatedBy(moviesId: Int): LiveData<Resource<TvShowsWithCreatedBy>> {
        return object : NetworkBoundResource<TvShowsWithCreatedBy, MoviesObject>(appExecutors) {
            override fun loadFromDB(): LiveData<TvShowsWithCreatedBy> =
                localDataSource.getTvShowsWithCreatedBy(moviesId)

            override fun shouldFetch(data: TvShowsWithCreatedBy?): Boolean =
                data?.mCreatedBy == null || data.mCreatedBy.isEmpty()

            override fun createCall(): LiveData<ApiResponse<MoviesObject>> =
                remoteDataSource.getDetailTvShowsAsLiveData(moviesId)

            override fun saveCallResult(data: MoviesObject) {
                val createdByList = ArrayList<CreatedByEntity>()
                for (createdResponse in data.createdBy) {
                    val director = CreatedByEntity(
                        createdId = createdResponse.id,
                        moviesId = moviesId,
                        name = createdResponse.name
                    )
                    createdByList.add(director)
                }
                localDataSource.insertCreatedTvShows(createdByList)

            }
        }.asLiveData()
    }

    // set favourites movies and tv shows
    override fun setFavoriteMovie(course: MoviesEntity, state: Boolean) {
        val runnable = { localDataSource.setFavoritesMovie(course, state) }
        appExecutors.diskIO().execute(runnable)
    }

    // load favourites movies
    override fun getFavoriteMoviesPage(): LiveData<Resource<PagedList<MoviesEntity>>> {
        return object : NetworkBoundResource<PagedList<MoviesEntity>, List<MoviesEntity>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MoviesEntity>> =
                LivePagedListBuilder(localDataSource.getFavoriteMoviesAsPaged(), 10).build()

            override fun shouldFetch(data: PagedList<MoviesEntity>?): Boolean = false

            override fun createCall(): LiveData<ApiResponse<List<MoviesEntity>>>? = null

            override fun saveCallResult(data: List<MoviesEntity>) {}
        }.asLiveData()
    }

    // load favourites tv shows
    override fun getFavoriteTvShowsPage(): LiveData<Resource<PagedList<MoviesEntity>>> {
        return object : NetworkBoundResource<PagedList<MoviesEntity>, List<MoviesEntity>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MoviesEntity>> =
                LivePagedListBuilder(localDataSource.getFavoriteTvShowsAsPaged(), 10).build()

            override fun shouldFetch(data: PagedList<MoviesEntity>?): Boolean = false

            override fun createCall(): LiveData<ApiResponse<List<MoviesEntity>>>? = null

            override fun saveCallResult(data: List<MoviesEntity>) {}
        }.asLiveData()
    }


    // load search movies
    override fun getSearchMoviesPage(query: String): LiveData<Resource<PagedList<MoviesEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MoviesEntity>, List<MoviesObject>>(appExecutors) {

            public override fun loadFromDB(): LiveData<PagedList<MoviesEntity>> =
                LivePagedListBuilder(localDataSource.getSearchMoviesByTitle(query), 10).build()

            override fun shouldFetch(data: PagedList<MoviesEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<MoviesObject>>> =
                remoteDataSource.getSearchMoviesAsLiveData(query)

            override fun saveCallResult(data: List<MoviesObject>) {}
        }.asLiveData()
    }

    // load search tv shows
    override fun getSearchTvShowsPage(query: String): LiveData<Resource<PagedList<MoviesEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MoviesEntity>, List<MoviesObject>>(appExecutors) {

            public override fun loadFromDB(): LiveData<PagedList<MoviesEntity>> =
                LivePagedListBuilder(localDataSource.getSearchTvShowsByName(query), 10).build()

            override fun shouldFetch(data: PagedList<MoviesEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<MoviesObject>>> =
                remoteDataSource.getSearchTvShowsAsLiveData(query)

            override fun saveCallResult(data: List<MoviesObject>) {}
        }.asLiveData()
    }


}