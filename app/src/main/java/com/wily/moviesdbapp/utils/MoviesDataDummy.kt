package com.wily.moviesdbapp.utils

import com.wily.moviesdbapp.data.source.local.entity.*

object MoviesDataDummy {

    // movies generate
    fun generateDummyMoviesWithGenres(movies: MoviesEntity, isMovies: Boolean): MoviesWithGenres {
        movies.isMovies = isMovies
        return MoviesWithGenres(movies, generateDummyGenres(movies.moviesId))
    }

    fun generateDummyMoviesWithDirector(
        movies: MoviesEntity,
        isMovies: Boolean
    ): MoviesWithDirector {
        movies.isMovies = isMovies
        return MoviesWithDirector(movies, generateDummyDirector(movies.moviesId))
    }

    // tv shows generate
    fun generateDummyTvShowsWithGenres(movies: MoviesEntity, isTvShows: Boolean): MoviesWithGenres {
        movies.isTvshows = isTvShows
        return MoviesWithGenres(movies, generateDummyGenres(movies.moviesId))
    }

    fun generateDummyTvShowsWithCreatedBy(
        movies: MoviesEntity,
        isTvShows: Boolean
    ): TvShowsWithCreatedBy {
        movies.isTvshows = isTvShows
        return TvShowsWithCreatedBy(movies, generateDummyCreatedBy(movies.moviesId))
    }


    fun generateDummyMovies(): List<MoviesEntity> {
        val movies = ArrayList<MoviesEntity>()
        val base_url = "https://image.tmdb.org/t/p/w500"

        movies.add(
            MoviesEntity(
                464052,
                "Wonder Woman 1984",
                "$base_url/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg",
                "Wonder Woman comes into conflict with the Soviet Union during the Cold War in the 1980s and finds a formidable foe by the name of the Cheetah.",
                "en",
                "English",
                "2020-12-16",
                6017.605,
                7.3,
                2172,
                200000000,
                85400000,
                "A new era of wonder begins.",
                "Released",
                "",
                "",
                "",
                "",
                false,
                true,
                false
            )
        )
        movies.add(
            MoviesEntity(
                529203,
                "The Croods: A New Age",
                "$base_url/tK1zy5BsCt1J4OzoDicXmr0UTFH.jpg",
                "After leaving their cave, the Croods encounter their biggest threat since leaving: another family called the Bettermans, who claim and show to be better and evolved. Grug grows suspicious of the Betterman parents, Phil and Hope,  as they secretly plan to break up his daughter Eep with her loving boyfriend Guy to ensure that their daughter Dawn has a loving and smart partner to protect her.",
                "en",
                "English",
                "2020-11-25",
                1937.566,
                8.1,
                420,
                65000000,
                35930000,
                "The future ain't what it used to be.",
                "Released",
                "",
                "",
                "",
                "",
                false,
                true,
                false
            )
        )


        return movies
    }

    fun generateDummyGenres(moviesId: Int): List<GenresEntity> {

        val modules = ArrayList<GenresEntity>()

        modules.add(
            GenresEntity(1, moviesId, "Adventure")
        )
        modules.add(
            GenresEntity(2, moviesId, "Fantasy")
        )
        modules.add(
            GenresEntity(3, moviesId, "Family")
        )
        modules.add(
            GenresEntity(4, moviesId, "Animation")
        )
        modules.add(
            GenresEntity(5, moviesId, "Sci-Fi & Fantasy")
        )
        modules.add(
            GenresEntity(6, moviesId, "Action & Adventure")
        )

        return modules
    }

    fun generateDummyDirector(moviesId: Int): List<DirectorEntity> {

        val modules = ArrayList<DirectorEntity>()

        modules.add(
            DirectorEntity(
                1,
                moviesId,
                "Patty Jenkins",
                "Director"
            )
        )
        modules.add(
            DirectorEntity(
                2,
                moviesId,
                "Joel Crawford",
                "Director"
            )
        )


        return modules
    }

    fun generateDummyTvShows(): List<MoviesEntity> {
        val tvshows = ArrayList<MoviesEntity>()
        val base_url = "https://image.tmdb.org/t/p/w500"

        tvshows.add(
            MoviesEntity(
                77169,
                "",
                "$base_url/obLBdhLxheKg8Li1qO11r2SwmYO.jpg",
                "This Karate Kid sequel series picks up 30 years after the events of the 1984 All Valley Karate Tournament and finds Johnny Lawrence on the hunt for redemption by reopening the infamous Cobra Kai karate dojo. This reignites his old rivalry with the successful Daniel LaRusso, who has been working to maintain the balance in his life without mentor Mr. Miyagi.",
                "en",
                "English",
                "",
                2490.713,
                8.1,
                1489,
                0,
                0,
                "Cobra Kai never dies.",
                "Returning Series",
                "Cobra Kai",
                "2018-05-02",
                "2021-01-01",
                "Scripted",
                false,
                false,
                true
            )
        )
        tvshows.add(
            MoviesEntity(
                44217,
                "",
                "$base_url/bQLrHIRNEkE3PdIWQrZHynQZazu.jpg",
                "The adventures of Ragnar Lothbrok, the greatest hero of his age. The series tells the sagas of Ragnar's band of Viking brothers and his family, as he rises to become King of the Viking tribes. As well as being a fearless warrior, Ragnar embodies the Norse traditions of devotion to the gods. Legend has it that he was a direct descendant of Odin, the god of war and warriors.",
                "en",
                "English",
                "",
                1614.937,
                8.0,
                3687,
                0,
                0,
                "",
                "Ended",
                "Vikings",
                "2013-03-03",
                "2020-12-30",
                "Scripted",
                false,
                false,
                true
            )
        )
        tvshows.add(
            MoviesEntity(
                82856,
                "",
                "$base_url/eLT8Cu357VOwBVTitkmlDEg32Fs.jpg",
                "After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter.",
                "en",
                "English",
                "",
                25.12,
                7.4,
                200,
                0,
                0,
                "Bounty hunting is a complicated profession.",
                "Returning Series",
                "The Mandalorian",
                "2019-11-12",
                "2020-12-18",
                "Scripted",
                false,
                false,
                true
            )
        )
        return tvshows
    }

    fun generateDummyCreatedBy(moviesId: Int): List<CreatedByEntity> {

        val modules = ArrayList<CreatedByEntity>()

        modules.add(
            CreatedByEntity(1, moviesId, "Hayden Schlossberg")
        )
        modules.add(
            CreatedByEntity(2, moviesId, "Michael Hirst")
        )
        modules.add(
            CreatedByEntity(3, moviesId, "Jon Favreau")
        )
        modules.add(
            CreatedByEntity(4, moviesId, "Jorge Guerricaechevarría")
        )

        return modules
    }

    //remote dummy
    fun generateRemoteDummyMovies(): List<MoviesEntity> {
        val movies = ArrayList<MoviesEntity>()
        val base_url = "https://image.tmdb.org/t/p/w500"

        movies.add(
            MoviesEntity(
                464052,
                "Wonder Woman 1984",
                "$base_url/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg",
                "Wonder Woman comes into conflict with the Soviet Union during the Cold War in the 1980s and finds a formidable foe by the name of the Cheetah.",
                "en",
                "English",
                "2020-12-16",
                6017.605,
                7.3,
                2172,
                200000000,
                85400000,
                "A new era of wonder begins.",
                "Released",
                "",
                "",
                "",
                "",
                false,
                true,
                false
            )
        )
        movies.add(
            MoviesEntity(
                529203,
                "The Croods: A New Age",
                "$base_url/tK1zy5BsCt1J4OzoDicXmr0UTFH.jpg",
                "After leaving their cave, the Croods encounter their biggest threat since leaving: another family called the Bettermans, who claim and show to be better and evolved. Grug grows suspicious of the Betterman parents, Phil and Hope,  as they secretly plan to break up his daughter Eep with her loving boyfriend Guy to ensure that their daughter Dawn has a loving and smart partner to protect her.",
                "en",
                "English",
                "2020-11-25",
                1937.566,
                8.1,
                420,
                65000000,
                35930000,
                "The future ain't what it used to be.",
                "Released",
                "",
                "",
                "",
                "",
                false,
                true,
                false
            )
        )


        return movies
    }

    fun generateRemoteDummyTvShows(): List<MoviesEntity> {
        val tvshows = ArrayList<MoviesEntity>()
        val base_url = "https://image.tmdb.org/t/p/w500"

        tvshows.add(
            MoviesEntity(
                77169,
                "",
                "$base_url/obLBdhLxheKg8Li1qO11r2SwmYO.jpg",
                "This Karate Kid sequel series picks up 30 years after the events of the 1984 All Valley Karate Tournament and finds Johnny Lawrence on the hunt for redemption by reopening the infamous Cobra Kai karate dojo. This reignites his old rivalry with the successful Daniel LaRusso, who has been working to maintain the balance in his life without mentor Mr. Miyagi.",
                "en",
                "English",
                "",
                2490.713,
                8.1,
                1489,
                0,
                0,
                "Cobra Kai never dies.",
                "Returning Series",
                "Cobra Kai",
                "2018-05-02",
                "2021-01-01",
                "Scripted",
                false,
                false,
                true
            )
        )
        tvshows.add(
            MoviesEntity(
                44217,
                "",
                "$base_url/bQLrHIRNEkE3PdIWQrZHynQZazu.jpg",
                "The adventures of Ragnar Lothbrok, the greatest hero of his age. The series tells the sagas of Ragnar's band of Viking brothers and his family, as he rises to become King of the Viking tribes. As well as being a fearless warrior, Ragnar embodies the Norse traditions of devotion to the gods. Legend has it that he was a direct descendant of Odin, the god of war and warriors.",
                "en",
                "English",
                "",
                1614.937,
                8.0,
                3687,
                0,
                0,
                "",
                "Ended",
                "Vikings",
                "2013-03-03",
                "2020-12-30",
                "Scripted",
                false,
                false,
                true
            )
        )
        tvshows.add(
            MoviesEntity(
                82856,
                "",
                "$base_url/eLT8Cu357VOwBVTitkmlDEg32Fs.jpg",
                "After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter.",
                "en",
                "English",
                "",
                25.12,
                7.4,
                200,
                0,
                0,
                "Bounty hunting is a complicated profession.",
                "Returning Series",
                "The Mandalorian",
                "2019-11-12",
                "2020-12-18",
                "Scripted",
                false,
                false,
                true
            )
        )
        return tvshows
    }

    fun generateRemoteDummyGenres(moviesId: Int): List<GenresEntity> {

        val modules = ArrayList<GenresEntity>()

        modules.add(
            GenresEntity(1, moviesId, "Adventure")
        )
        modules.add(
            GenresEntity(2, moviesId, "Fantasy")
        )
        modules.add(
            GenresEntity(3, moviesId, "Family")
        )
        modules.add(
            GenresEntity(4, moviesId, "Animation")
        )
        modules.add(
            GenresEntity(5, moviesId, "Sci-Fi & Fantasy")
        )
        modules.add(
            GenresEntity(6, moviesId, "Action & Adventure")
        )

        return modules
    }

    fun generateRemoteDummyDirector(moviesId: Int): List<DirectorEntity> {

        val modules = ArrayList<DirectorEntity>()

        modules.add(
            DirectorEntity(
                1,
                moviesId,
                "Patty Jenkins",
                "Director"
            )
        )
        modules.add(
            DirectorEntity(
                2,
                moviesId,
                "Joel Crawford",
                "Director"
            )
        )

        return modules
    }

    fun generateRemoteDummyCreatedBy(moviesId: Int): List<CreatedByEntity> {

        val modules = ArrayList<CreatedByEntity>()

        modules.add(
            CreatedByEntity(1, moviesId, "Hayden Schlossberg")
        )
        modules.add(
            CreatedByEntity(2, moviesId, "Michael Hirst")
        )
        modules.add(
            CreatedByEntity(3, moviesId, "Jon Favreau")
        )
        modules.add(
            CreatedByEntity(4, moviesId, "Jorge Guerricaechevarría")
        )

        return modules
    }

}