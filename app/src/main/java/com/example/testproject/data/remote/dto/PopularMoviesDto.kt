package com.example.testproject.data.remote.dto

data class PopularMoviesDto(
    val page: Int?,
    val results: List<ResultDto>?,
){
    data class ResultDto(
        val adult: Boolean?,
        val backdrop_path: String?,
        val id: Int?,
        val original_title: String?,
        val overview: String?,
        val popularity: Double?,
        val release_date: String?,
        val title: String?,
    )
}