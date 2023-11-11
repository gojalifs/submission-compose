package com.ngapak.dev.latihan.watchit.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ngapak.dev.latihan.watchit.data.repository.MoviesRepository
import com.ngapak.dev.latihan.watchit.ui.screen.home.HomeScreenViewModel
import com.ngapak.dev.latihan.watchit.ui.screen.movie_detail.MovieDetailViewModel
import com.ngapak.dev.latihan.watchit.ui.screen.search.SearchViewModel

class ViewModelFactory(private val repository: MoviesRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            return HomeScreenViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            return MovieDetailViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}