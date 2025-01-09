package com.example.testproject.presentation.screens.screens.homePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.core.OperationStatus
import com.example.testproject.data.remote.dto.PopularMoviesDto
import com.example.testproject.data.repository.FirebaseAuthRepositoryImpl
import com.example.testproject.data.repository.MoviesRepositoryImpl
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val userInfoEmail = FirebaseAuthRepositoryImpl()
    private val movieRepository = MoviesRepositoryImpl()

    private var _userEmail = MutableSharedFlow<String>()
    val userEmail: SharedFlow<String> = _userEmail

    private var _popularMovies = MutableStateFlow<PopularMoviesDto?>(null)
    val popularMovies: StateFlow<PopularMoviesDto?> = _popularMovies

    init {
        getUserEmail()
        getPopularMovies()
    }

    // Fetch the current user's email and emit it into the flow
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
        when (val status = movieRepository.getPopularMovies()) {
            is OperationStatus.Success -> {
                _popularMovies.emit(status.value)
            }
            is OperationStatus.Failure -> {}
        }
    }
}