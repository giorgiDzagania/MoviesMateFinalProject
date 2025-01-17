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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val userInfoEmail = FirebaseAuthRepositoryImpl()
    private val movieRepository = MoviesRepositoryImpl()

    private var _userEmail = MutableStateFlow<String?>(null)
    val userEmail: StateFlow<String?> = _userEmail

    private var _popularMovies = MutableStateFlow<PopularMoviesDto?>(null)
    val popularMovies: StateFlow<PopularMoviesDto?> = _popularMovies

    private val _upcomingMovies = MutableStateFlow<UpcomingMoviesDto?>(null)
    val upcomingMovies: StateFlow<UpcomingMoviesDto?> = _upcomingMovies

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

    private suspend fun fetchPopularMovies() {
        when (val status = movieRepository.getPopularMovies()) {
            is OperationStatus.Success -> {
                _popularMovies.emit(status.value)
            }

            is OperationStatus.Failure -> {}
        }
    }

    private suspend fun fetchUpcomingMovies() {
        when (val status = movieRepository.getUpcomingMovies()) {
            is OperationStatus.Success -> {
                _upcomingMovies.emit(status.value)
            }

            is OperationStatus.Failure -> {}
        }
    }

    fun saveMovie(movie: Movies) = viewModelScope.launch {
        movieRepository.saveMovie(movie = movie)
    }
}
