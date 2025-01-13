package com.example.testproject.presentation.screens.screens.detailsPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testproject.core.OperationStatus
import com.example.testproject.data.remote.dto.MovieDetailsDto
import com.example.testproject.data.remote.dto.VideosDto
import com.example.testproject.data.repository.MoviesRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    private val moviesRepository = MoviesRepositoryImpl()

    private val _movieDetails = MutableStateFlow<MovieDetailsDto?>(null)
    val movieDetails: StateFlow<MovieDetailsDto?> = _movieDetails

    private val _videoTrailer = MutableStateFlow<VideosDto?>(null)
    val videoTrailer: StateFlow<VideosDto?> = _videoTrailer

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage


    fun fetchMovieDetailsAndTrailer(movieId: String) = viewModelScope.launch {
        try {
            // Fetch Movie Details
            when (val detailsResult = moviesRepository.getMovieDetails(movieId)) {
                is OperationStatus.Success -> _movieDetails.emit(detailsResult.value)
                is OperationStatus.Failure -> _errorMessage.emit("Failed to fetch movie details: ${detailsResult.exception}")
            }

            // Fetch Video Trailer
            when (val videoResult = moviesRepository.getVideoTrailer(movieId)) {
                is OperationStatus.Success -> _videoTrailer.emit(videoResult.value)
                is OperationStatus.Failure -> _errorMessage.emit("Failed to fetch video trailer: ${videoResult.exception}")
            }
        } catch (e: Exception) {
            _errorMessage.emit("An unexpected error occurred: $e")
        }
    }

   /*

    fun getMovieDetails(movieId: String) = viewModelScope.launch {


        when (val status = moviesRepository.getMovieDetails(movieId)) {
            is OperationStatus.Success -> {
                _movieDetails.emit(status.value)
            }

            is OperationStatus.Failure -> {
                _errorMessage.emit("Failed to fetch data: ${status.exception}")
            }
        }

    }*/
}