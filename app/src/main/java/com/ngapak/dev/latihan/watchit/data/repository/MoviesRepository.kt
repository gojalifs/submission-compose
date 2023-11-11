package com.ngapak.dev.latihan.watchit.data.repository

import com.ngapak.dev.latihan.watchit.data.local.MovieDatabase
import com.ngapak.dev.latihan.watchit.data.local.entities.MoviesEntity
import com.ngapak.dev.latihan.watchit.data.remote.ApiService
import kotlinx.coroutines.flow.flow

class MoviesRepository private constructor(
    private val apiService: ApiService,
    private val database: MovieDatabase
) {
    fun getMoviesLocal() = flow {
        emit(database.moviesDao().getMovies())
        getMovies().collect {
            emit(it) //data from api
        }
    }

    private fun getMovies() = flow {
        val movies = apiService.getMovies().results.mapNotNull { it }
        var movieEntity: MoviesEntity
        val moviesEntities = movies.map {
            movieEntity = MoviesEntity(it.id, it.title, it.posterPath, it.voteAverage, it.voteCount)
            movieEntity
        }
        database.moviesDao().insert(moviesEntities)
        emit(moviesEntities)
    }

    fun getMovieDetail(movieId: Int) = flow {
        emit(apiService.getMovieDetail(movieId))
    }

    companion object {
        @Volatile
        private var instance: MoviesRepository? = null
        fun geInstance(apiService: ApiService, database: MovieDatabase) =
            instance ?: synchronized(this) {
                instance ?: MoviesRepository(apiService, database)
            }.also { instance = it }
    }
}