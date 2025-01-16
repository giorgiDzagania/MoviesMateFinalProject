package com.example.testproject.presentation.screens.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.core.OperationStatus
import com.example.testproject.data.repository.MoviesRepositoryImpl
import com.example.testproject.domain.model.Movies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {
    private val repository = MoviesRepositoryImpl()

    private val _allSavedMovies = MutableStateFlow<List<Movies>>(emptyList())
    val allSavedMovies: StateFlow<List<Movies>> = _allSavedMovies

    fun showAllSavedMovies() = viewModelScope.launch {
        when (val status = repository.getAllSavedMovies()) {
            is OperationStatus.Success -> {
                _allSavedMovies.emit(status.value)
            }

            is OperationStatus.Failure -> {}
        }
    }

    fun deleteSavedMovie(movies: Movies) = viewModelScope.launch {
        when (repository.deleteMovie(movies)) {
            is OperationStatus.Success -> {
                showAllSavedMovies()
            }

            is OperationStatus.Failure -> {}
        }
    }


}