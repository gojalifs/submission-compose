package com.ngapak.dev.latihan.watchit.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngapak.dev.latihan.watchit.data.local.entities.MoviesEntity
import com.ngapak.dev.latihan.watchit.data.repository.MoviesRepository
import com.ngapak.dev.latihan.watchit.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {
    private var _uiState = MutableStateFlow<UiState<List<MoviesEntity?>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<MoviesEntity?>>> get() = _uiState

    init {
        getMovies()
    }

    fun getMovies() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            moviesRepository.getMoviesLocal()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { movies ->
                    _uiState.value = UiState.Success(movies)
                }
        }
    }
}