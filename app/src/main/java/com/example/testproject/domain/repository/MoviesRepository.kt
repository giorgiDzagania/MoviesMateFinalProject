package com.example.testproject.domain.repository

import com.example.testproject.core.OperationStatus
import com.example.testproject.data.remote.dto.MovieDto
import com.example.testproject.data.remote.dto.PopularMoviesDto
import com.example.testproject.data.remote.dto.UpcomingMoviesDto

interface MoviesRepository {

    suspend fun getPopularMovies(): OperationStatus<PopularMoviesDto>
    suspend fun getUpcomingMovies(): OperationStatus<UpcomingMoviesDto>

    suspend fun getSearchedMovie(query: String): OperationStatus<List<MovieDto>>

}
