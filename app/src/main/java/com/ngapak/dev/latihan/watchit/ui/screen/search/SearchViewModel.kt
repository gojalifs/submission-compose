package com.ngapak.dev.latihan.watchit.ui.screen.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngapak.dev.latihan.watchit.data.local.entities.MoviesEntity
import com.ngapak.dev.latihan.watchit.data.repository.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: MoviesRepository) : ViewModel() {
    private var movies = mutableListOf<MoviesEntity>()
    private var initiated = false;

    private var _searchedMovie = MutableStateFlow<MutableList<MoviesEntity>>(mutableListOf())
    val searchedMovie: StateFlow<List<MoviesEntity>> = _searchedMovie

    private val _query = mutableStateOf("")
    var query: State<String> = _query

    private fun getMovies() {
        viewModelScope.launch {
            repository.getMoviesLocal().collect {
                movies = it.toMutableList()
                _searchedMovie.value = movies
            }
        }
    }

    fun searchMovie(newQuery: String) {
        if (!initiated) {
            getMovies()
            initiated = true
        }
        _query.value = newQuery
        _searchedMovie.value = movies.filter {
            it.title?.contains(newQuery, true) ?: false
        }.sortedBy { it.title }.toMutableList()
    }
}