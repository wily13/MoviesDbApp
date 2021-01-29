package com.wily.moviesdbapp.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wily.moviesdbapp.data.source.local.entity.CreatedByEntity
import com.wily.moviesdbapp.data.source.local.entity.DirectorEntity
import com.wily.moviesdbapp.data.source.local.entity.GenresEntity
import com.wily.moviesdbapp.data.source.local.entity.MoviesEntity

@Database(
    entities = [MoviesEntity::class, GenresEntity::class, DirectorEntity::class, CreatedByEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getInstance(context: Context): MoviesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDatabase::class.java,
                    "Movies_db"
                ).build()
            }
    }
}