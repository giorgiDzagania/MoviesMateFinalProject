package com.example.testproject.data.remote.dto

data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val backdrop_path: String?,
    val releaseDate: String?,
    val genreIds: List<Int>
)

data class ApiResponse<T>(
    val page: Int,
    val results: List<T>,
    val totalResults: Int,
    val totalPages: Int
)