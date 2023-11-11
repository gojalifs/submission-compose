package com.ngapak.dev.latihan.watchit.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ngapak.dev.latihan.watchit.data.local.dao.FavoriteDao
import com.ngapak.dev.latihan.watchit.data.local.dao.MoviesDao
import com.ngapak.dev.latihan.watchit.data.local.entities.FavoriteEntity
import com.ngapak.dev.latihan.watchit.data.local.entities.MoviesEntity

@Database(
    entities = [FavoriteEntity::class, MoviesEntity::class],
    version = 2,
    exportSchema = false,
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun moviesDao(): MoviesDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java, "movie_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}