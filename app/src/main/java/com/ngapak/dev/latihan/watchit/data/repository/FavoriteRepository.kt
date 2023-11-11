package com.ngapak.dev.latihan.watchit.data.repository

import android.util.Log
import com.ngapak.dev.latihan.watchit.data.local.MovieDatabase
import com.ngapak.dev.latihan.watchit.data.local.entities.FavoriteEntity
import kotlinx.coroutines.flow.flow
import java.util.concurrent.Executors

class FavoriteRepository private constructor(private val movieDatabase: MovieDatabase) {
    private val dao = movieDatabase.favoriteDao()
    private val executorService = Executors.newSingleThreadExecutor()

    fun getFavoriteMovies() = flow {
        Log.d("TAG", "isFavorite: movies initiated")

        emit(dao.getMovies())
    }

    fun addFavorite(movie: FavoriteEntity) {
        try {
            executorService.execute { dao.insertFavoriteMovie(movie) }
        } catch (e: Exception) {
            throw e
        }
    }

    fun deleteFavorite(movie: FavoriteEntity) {
        try {
            executorService.execute { dao.deleteMovie(movie) }
        } catch (e: Exception) {
            throw e
        }

    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun geInstance(movieDatabase: MovieDatabase) =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(movieDatabase)
            }.also { instance = it }
    }
}