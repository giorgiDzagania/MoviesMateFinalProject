package com.example.testproject.data

import com.example.testproject.data.local.entity.MovieDbo
import com.example.testproject.data.remote.dto.PopularMoviesDto
import com.example.testproject.data.remote.dto.UpcomingMoviesDto
import com.example.testproject.domain.model.Movies


// --------------------------- API -----------------------

// --- PopularMoviesDto to MovieList ----
fun PopularMoviesDto.toListMovie(): List<Movies> {
    return results?.map { it.toMovie() } ?: emptyList()
}

fun PopularMoviesDto.ResultDto.toMovie(): Movies {
    return Movies(
        id = id ?: 0,
        image = backdrop_path.orEmpty(),
        title = title.orEmpty()
    )
}


// --- UpcomingMoviesDto to MovieList -----
fun UpcomingMoviesDto.toListMovie(): List<Movies> {
    return results?.map { it.toMovies() } ?: emptyList()
}

fun UpcomingMoviesDto.Result.toMovies(): Movies {
    return Movies(
        id = this.id,
        image = this.poster_path,
        title = this.title
    )
}

// --------------------------------- Room ------------------------
// Dto to domain
fun Movies.toMoviesDbo(userEmail: String) = MovieDbo(
    id, image, title, userEmail
)

// MovieDbo -> Movie
fun MovieDbo.toMovies() = Movies(
    id, image, title
)