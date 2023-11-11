package com.ngapak.dev.latihan.watchit.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngapak.dev.latihan.watchit.data.local.entities.FavoriteEntity
import com.ngapak.dev.latihan.watchit.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {
    private var _moviesState = MutableStateFlow<MutableList<FavoriteEntity>?>(mutableListOf())
    val moviesState: StateFlow<List<FavoriteEntity>?> = _moviesState

    fun getFavorites() {
        viewModelScope.launch {
            favoriteRepository.getFavoriteMovies()
                .catch {
                    _moviesState.value = null
                }
                .collect { movies ->
                    _moviesState.value = movies.toMutableList()
                }
        }
    }

    fun addFavorite(id: Int, posterPath: String, title: String) {
        val movie = FavoriteEntity(id, posterPath, title)
        favoriteRepository.addFavorite(movie)

    }

    fun deleteFavorite(id: Int, posterPath: String, title: String) {
        val movie = FavoriteEntity(id, posterPath, title)
        _moviesState.value?.remove(movie)
        favoriteRepository.deleteFavorite(movie)
    }
}