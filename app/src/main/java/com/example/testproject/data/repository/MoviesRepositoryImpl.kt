package com.example.testproject.data.repository

import com.example.testproject.core.CallHelper
import com.example.testproject.core.OperationStatus
import com.example.testproject.data.remote.RetrofitInstance
import com.example.testproject.data.remote.dto.PopularMoviesDto
import com.example.testproject.domain.repository.MoviesRepository

class MoviesRepositoryImpl : MoviesRepository {
    private val service = RetrofitInstance.moviesService

    override suspend fun getPopularMovies(): OperationStatus<PopularMoviesDto> {
        return CallHelper.safeApiCall {
            service.getPopularMovies()
        }
    }

}