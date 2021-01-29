package com.wily.moviesdbapp.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class MoviesWithGenres(
    @Embedded
    var mMovies: MoviesEntity,

    @Relation(parentColumn = "moviesId", entityColumn = "moviesId")
    var mGenres: List<GenresEntity>
)