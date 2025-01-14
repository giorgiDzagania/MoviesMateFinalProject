package com.example.testproject.domain.repository

import com.example.testproject.core.OperationStatus
import com.example.testproject.data.remote.dto.MovieDetailsDto
import com.example.testproject.data.remote.dto.MovieDto
import com.example.testproject.data.remote.dto.PopularMoviesDto
import com.example.testproject.data.remote.dto.UpcomingMoviesDto
import com.example.testproject.data.remote.dto.VideosDto
import com.example.testproject.domain.model.Movies

interface MoviesRepository {

    suspend fun getPopularMovies(): OperationStatus<PopularMoviesDto>
    suspend fun getUpcomingMovies(): OperationStatus<UpcomingMoviesDto>
    suspend fun getSearchedMovie(query: String): OperationStatus<List<MovieDto>>
    suspend fun getMovieDetails(movieId: String): OperationStatus<MovieDetailsDto>
    suspend fun getVideoTrailer(movieId: String): OperationStatus<VideosDto>

    suspend fun saveMovie(movie: Movies): OperationStatus<Unit>
    suspend fun deleteMovie(movie: Movies): OperationStatus<Unit>
    suspend fun getAllSavedMovies(): OperationStatus<List<Movies>>

}
