package com.example.testproject.data.remote.service

import com.example.testproject.data.remote.dto.ApiResponse
import com.example.testproject.data.remote.dto.MovieDto
import com.example.testproject.data.remote.dto.PopularMoviesDto
import com.example.testproject.data.remote.dto.UpcomingMoviesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = "ecf551cd79c0550487694d36dfc0514c"
    ): Response<PopularMoviesDto>


    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 2,
        @Query("api_key") apiKey: String = "ecf551cd79c0550487694d36dfc0514c"
    ) : Response<UpcomingMoviesDto>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = "ecf551cd79c0550487694d36dfc0514c"
    ): Response<ApiResponse<MovieDto>>

}