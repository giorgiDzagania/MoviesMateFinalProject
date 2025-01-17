package com.example.testproject.data.repository

import android.telecom.Call
import com.example.testproject.MovieApplication
import com.example.testproject.core.CallHelper
import com.example.testproject.core.OperationStatus
import com.example.testproject.core.map
import com.example.testproject.data.local.MovieDatabase
import com.example.testproject.data.remote.RetrofitInstance
import com.example.testproject.data.remote.dto.MovieDetailsDto
import com.example.testproject.data.remote.dto.MovieDto
import com.example.testproject.data.remote.dto.PopularMoviesDto
import com.example.testproject.data.remote.dto.UpcomingMoviesDto
import com.example.testproject.data.remote.dto.VideosDto
import com.example.testproject.data.toMovies
import com.example.testproject.data.toMoviesDbo
import com.example.testproject.domain.model.Movies
import com.example.testproject.domain.repository.MoviesRepository

class MoviesRepositoryImpl : MoviesRepository {
    private val service = RetrofitInstance.moviesService
    private val movieDao = MovieDatabase.create(MovieApplication.context).movieDao

    override suspend fun getPopularMovies(): OperationStatus<PopularMoviesDto> {
        return CallHelper.safeApiCall {
            service.getPopularMovies()
        }
    }

    override suspend fun getUpcomingMovies(): OperationStatus<UpcomingMoviesDto> {
        return CallHelper.safeApiCall {
            service.getUpcomingMovies()
        }
    }

    override suspend fun getSearchedMovie(query: String): OperationStatus<List<MovieDto>> {
        return CallHelper.safeApiResponseCall {
            service.searchMovies(query = query)
        }
    }

    override suspend fun getMovieDetails(movieId: String): OperationStatus<MovieDetailsDto> {
        return CallHelper.safeApiCall {
            service.getMovieDetails(movieId)
        }
    }

    override suspend fun getVideoTrailer(movieId: String): OperationStatus<VideosDto> {
        return CallHelper.safeApiCall {
            service.getVideoTrailer(movieId)
        }
    }

    // Movie -> MovieDbo (Room save locally)
    override suspend fun saveMovie(movie: Movies): OperationStatus<Unit> {
        return CallHelper.safeRoomCall {
            movieDao.saveToFavorites(movie = movie.toMoviesDbo())
        }
    }

    override suspend fun deleteMovie(movie: Movies): OperationStatus<Unit> {
        return CallHelper.safeRoomCall {
            movieDao.deleteFromFavorites(movie = movie.toMoviesDbo())
        }
    }

    // MovieDbo -> Movie
    override suspend fun getAllSavedMovies(): OperationStatus<List<Movies>> {
        return CallHelper.safeRoomCall {
            movieDao.getAllFavorites().map { movieDbo -> movieDbo.toMovies() }
        }
    }

}