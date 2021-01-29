package com.wily.moviesdbapp.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {

    const val DEFAULT = "Default"
    const val NEWEST = "Newest"
    const val NAMEASC = "NameAsc"
    const val NAMEDESC = "NameDesc"
    const val TITLEASC = "TitleAsc"
    const val TITLEDESC = "TitleDesc"
    const val POPULAR = "Popular"

    fun getMoviesSortedQuery(filter: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM moviesentities where isMovies = 1 ")
        if (filter == DEFAULT) {
            simpleQuery.append("")
        } else if (filter == NEWEST) {
            simpleQuery.append("ORDER BY moviesId DESC")
        } else if (filter == TITLEASC) {
            simpleQuery.append("ORDER BY title ASC")
        } else if (filter == TITLEDESC) {
            simpleQuery.append("ORDER BY title DESC")
        }  else if (filter == POPULAR) {
            simpleQuery.append("ORDER BY popularity DESC")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }

    fun getTvShowsSortedQuery(filter: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM moviesentities where isTvshows = 1 ")
        if (filter == DEFAULT) {
            simpleQuery.append("")
        } else if (filter == NEWEST) {
            simpleQuery.append("ORDER BY moviesId DESC")
        } else if (filter == NAMEASC) {
            simpleQuery.append("ORDER BY name ASC")
        } else if (filter == NAMEDESC) {
            simpleQuery.append("ORDER BY name DESC")
        } else if (filter == POPULAR) {
            simpleQuery.append("ORDER BY popularity DESC")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}