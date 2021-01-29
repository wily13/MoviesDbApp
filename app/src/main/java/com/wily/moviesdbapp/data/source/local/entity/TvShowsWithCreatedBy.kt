package com.wily.moviesdbapp.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation


data class TvShowsWithCreatedBy(
    @Embedded
	var mMovies: MoviesEntity,

    @Relation(parentColumn = "moviesId", entityColumn = "moviesId")
	var mCreatedBy: List<CreatedByEntity>
)