package com.example.testproject.domain.model

data class MovieDetails(
    val originalTitle: String,
    val voteAverage: Double,
    val releaseDate: String?,
    val genres: List<Genre>,
    val overview: String?,
    val trailerKey: String?
) {
    data class Genre(
        val name: String
    )
}