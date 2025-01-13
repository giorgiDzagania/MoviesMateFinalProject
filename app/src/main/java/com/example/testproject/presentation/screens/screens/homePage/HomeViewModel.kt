package com.example.testproject.presentation.screens.screens.homePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.core.OperationStatus
import com.example.testproject.data.remote.dto.PopularMoviesDto
import com.example.testproject.data.remote.dto.UpcomingMoviesDto
import com.example.testproject.data.repository.FirebaseAuthRepositoryImpl
import com.example.testproject.data.repository.MoviesRepositoryImpl
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
        getPopularMovies()
        getUpcomingMovies()
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

    private fun getPopularMovies() = viewModelScope.launch {
        _isLoadingState.emit(true)
        delay(3000L)
        when (val status = movieRepository.getPopularMovies()) {
            is OperationStatus.Success -> {
                _popularMovies.emit(status.value)
            }

            is OperationStatus.Failure -> {}
        }
        _isLoadingState.emit(false)
    }

    private fun getUpcomingMovies() = viewModelScope.launch {
       _isLoadingState.emit(true)
        when (val status = movieRepository.getUpcomingMovies()) {
            is OperationStatus.Success -> {
                _upcomingMovies.emit(status.value)
            }

            is OperationStatus.Failure -> {}
        }
        _isLoadingState.emit(false)
    }
}