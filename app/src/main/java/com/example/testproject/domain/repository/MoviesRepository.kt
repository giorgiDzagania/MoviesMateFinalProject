package com.example.testproject.domain.repository

import com.example.testproject.core.OperationStatus
import com.example.testproject.data.remote.dto.MovieDetailsDto
import com.example.testproject.data.remote.dto.MovieDto
import com.example.testproject.data.remote.dto.UpcomingMoviesDto
import com.example.testproject.data.remote.dto.VideosDto
import com.example.testproject.domain.model.Movies

interface MoviesRepository {

    // ----------------------- API --------------------------
    suspend fun getPopularMovies(): OperationStatus<List<Movies>>
    suspend fun getUpcomingMovies(): OperationStatus<List<Movies>>


    suspend fun getSearchedMovie(query: String): OperationStatus<List<MovieDto>>

    // ------ Details -----
    suspend fun getMovieDetails(movieId: String): OperationStatus<MovieDetailsDto>
    suspend fun getVideoTrailer(movieId: String): OperationStatus<VideosDto>


    // ----------------------- Room ---------------------------
    suspend fun saveMovie(movies: Movies): OperationStatus<Unit>
    suspend fun deleteMovie(movies: Movies): OperationStatus<Unit>
    suspend fun getAllSavedMovies(): OperationStatus<List<Movies>>

}
