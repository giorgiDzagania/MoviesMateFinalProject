package com.example.testproject.data

import com.example.testproject.data.local.entity.MovieDbo
import com.example.testproject.data.remote.dto.UpcomingMoviesDto
import com.example.testproject.domain.model.Movies

// Dto to domain
fun Movies.toMoviesDbo() = MovieDbo(
    id, image, title
)

fun UpcomingMoviesDto.Result.toMovies(): Movies {
    return Movies(
        id = this.id,
        image = this.poster_path,
        title = this.title
    )
}

// MovieDbo -> Movie
fun MovieDbo.toMovies() = Movies(
    id, image, title
)