package com.example.testproject.presentation.screens.screens.searchPage

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.core.OperationStatus
import com.example.testproject.data.remote.dto.MovieDto
import com.example.testproject.data.repository.MoviesRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val moviesRepo = MoviesRepositoryImpl()

    // StateFlow to hold the current search query
    private val _searchQuery = MutableStateFlow<String>("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    // StateFlow to hold loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    // StateFlow to hold the search results (movies)
    private val _searchedMovies = MutableStateFlow<List<MovieDto>>(emptyList())
    val searchedMovies: StateFlow<List<MovieDto>> get() = _searchedMovies

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        searchMovies(query)
    }

    private fun searchMovies(query: String) = viewModelScope.launch {
        _isLoading.emit(true)  // Start loading
        when (val status = moviesRepo.getSearchedMovie(query = query)) {
            is OperationStatus.Success -> {
                _searchedMovies.emit(status.value)
                Log.d("SearchViewModel", "Fetched movies: ${status.value}")
            }
            is OperationStatus.Failure -> {
                _errorMessage.emit("Failed to fetch data: ${status.exception}")
                Log.e("SearchViewModel", "Error: ${status.exception}")
            }
        }
        _isLoading.emit(false)
    }

}