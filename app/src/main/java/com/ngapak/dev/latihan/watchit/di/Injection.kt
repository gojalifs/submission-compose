package com.ngapak.dev.latihan.watchit.di

import android.content.Context
import com.ngapak.dev.latihan.watchit.BuildConfig
import com.ngapak.dev.latihan.watchit.data.local.MovieDatabase
import com.ngapak.dev.latihan.watchit.data.remote.ApiConfig
import com.ngapak.dev.latihan.watchit.data.repository.FavoriteRepository
import com.ngapak.dev.latihan.watchit.data.repository.MoviesRepository

object Injection {
    fun provideMoviesRepository(context: Context): MoviesRepository {
        val apiService = ApiConfig.getApiService(token = BuildConfig.TOKEN)
        val database = MovieDatabase.getDatabase(context)
        return MoviesRepository.geInstance(apiService, database)
    }

    fun provideFavoriteRepository(context: Context): FavoriteRepository {
        val movieDatabase = MovieDatabase.getDatabase(context)
        return FavoriteRepository.geInstance(movieDatabase)
    }
}