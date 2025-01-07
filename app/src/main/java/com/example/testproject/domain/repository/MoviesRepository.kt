package com.example.testproject.domain.repository

import com.example.testproject.core.OperationStatus
import com.example.testproject.data.remote.dto.PopularMoviesDto

interface MoviesRepository {

    suspend fun getPopularMovies(): OperationStatus<PopularMoviesDto>

}