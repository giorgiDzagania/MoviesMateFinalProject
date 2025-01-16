package com.example.testproject.presentation.screens.screens.homePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.core.OperationStatus
import com.example.testproject.data.remote.dto.PopularMoviesDto
import com.example.testproject.data.remote.dto.UpcomingMoviesDto
import com.example.testproject.data.repository.FirebaseAuthRepositoryImpl
import com.example.testproject.data.repository.MoviesRepositoryImpl
import com.example.testproject.domain.model.Movies
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val userInfoEmail = FirebaseAuthRepositoryImpl()
    private val movieRepository = MoviesRepositoryImpl()

    private var _userEmail = MutableStateFlow<String?>(null)
    val userEmail: StateFlow<String?> = _userEmail

    // -- Popular Movies --
    private var _popularMovies = MutableStateFlow<List<Movies>>(emptyList())
    val popularMovies: StateFlow<List<Movies>> = _popularMovies

    // -- Upcoming Movies --
    private val _upcomingMovies = MutableStateFlow<List<Movies>>(emptyList())
    val upcomingMovies: StateFlow<List<Movies>> = _upcomingMovies

    private val _isLoadingState = MutableStateFlow(false)
    val isLoadingState: StateFlow<Boolean> = _isLoadingState

    init {
        getUserEmail()
        fetchAllMovies()
    }

    private fun getUserEmail() = viewModelScope.launch {
        when (val status = userInfoEmail.getUserEmail()) {
            is OperationStatus.Success -> {
                _userEmail.emit(status.value)
            }

            is OperationStatus.Failure -> {
                _userEmail.emit("Error fetching email")
            }
        }
    }

    private fun fetchAllMovies() = viewModelScope.launch {
        _isLoadingState.emit(true)
        coroutineScope {
            try {
                val popularMoviesJob = async { fetchPopularMovies() }
                val upcomingMoviesJob = async { fetchUpcomingMovies() }
                awaitAll(popularMoviesJob, upcomingMoviesJob)
            } catch (e: Exception) {

            } finally {
                _isLoadingState.emit(false) // Hide loading
            }
        }
    }

    // ---------------------- Popular Movies ------------------
    private suspend fun fetchPopularMovies() {
        when (val status = movieRepository.getPopularMovies()) {
            is OperationStatus.Success -> {
                _popularMovies.emit(status.value)
            }

            is OperationStatus.Failure -> {}
        }
    }

    // --------------------- Upcoming Movies -------------------
    private suspend fun fetchUpcomingMovies() {
        when (val status = movieRepository.getUpcomingMovies()) {
            is OperationStatus.Success -> {
                _upcomingMovies.emit(status.value)
            }

            is OperationStatus.Failure -> {}
        }
    }

    fun saveMovie(movies: Movies) = viewModelScope.launch {
        movieRepository.saveMovie(movies = movies)
    }
}
