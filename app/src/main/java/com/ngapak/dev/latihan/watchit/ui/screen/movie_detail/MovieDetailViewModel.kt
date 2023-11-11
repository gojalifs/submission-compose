package com.ngapak.dev.latihan.watchit.ui.screen.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngapak.dev.latihan.watchit.data.remote.model.MovieDetailResponse
import com.ngapak.dev.latihan.watchit.data.repository.MoviesRepository
import com.ngapak.dev.latihan.watchit.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {
    private var _uiState = MutableStateFlow<UiState<MovieDetailResponse>>(UiState.Loading)
    val uiState: StateFlow<UiState<MovieDetailResponse>> get() = _uiState

    init {
        _uiState.value = UiState.Loading
    }

    fun getMovieDetail(movieId: Int) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            moviesRepository.getMovieDetail(movieId)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { movie ->
                    _uiState.value = UiState.Success(movie)
                }
        }
    }
}