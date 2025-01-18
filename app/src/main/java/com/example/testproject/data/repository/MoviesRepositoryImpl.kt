package com.example.testproject.data.repository

import com.example.testproject.MovieApplication
import com.example.testproject.core.CallHelper
import com.example.testproject.core.OperationStatus
import com.example.testproject.core.map
import com.example.testproject.data.local.MovieDatabase
import com.example.testproject.data.remote.RetrofitInstance
import com.example.testproject.data.remote.dto.MovieDetailsDto
import com.example.testproject.data.remote.dto.MovieDto
import com.example.testproject.data.remote.dto.VideosDto
import com.example.testproject.data.toListMovie
import com.example.testproject.data.toMovies
import com.example.testproject.data.toMoviesDbo
import com.example.testproject.domain.model.Movies
import com.example.testproject.domain.repository.MoviesRepository
import com.google.firebase.auth.FirebaseAuth

class MoviesRepositoryImpl : MoviesRepository {
    private val service = RetrofitInstance.moviesService
   // private val db = MovieDatabase.deleteDatabase(MovieApplication.context)
    private val movieDao = MovieDatabase.create(MovieApplication.context).movieDao


    // current user email for Room database
    private val userEmail = FirebaseAuth.getInstance().currentUser?.email.toString()


    // --------------------------- Popular Movie -----------------------------
    override suspend fun getPopularMovies(): OperationStatus<List<Movies>> {
        return CallHelper.safeApiCall {
            service.getPopularMovies()
        }.map { popularMoviesDto ->
            popularMoviesDto.toListMovie()
        }
    }


    // --------------------------- Upcoming Movies ------------------------------
    override suspend fun getUpcomingMovies(): OperationStatus<List<Movies>> {
        return CallHelper.safeApiCall {
            service.getUpcomingMovies()
        }.map { upcomingMoviesDto ->
            upcomingMoviesDto.toListMovie()
        }
    }

    // --------------------------- Search Movies ------------------------------
    override suspend fun getSearchedMovie(query: String): OperationStatus<List<MovieDto>> {
        return CallHelper.safeApiResponseCall {
            service.searchMovies(query = query)
        }
    }


    // -------------------------------- Details ---------------------------
    override suspend fun getMovieDetails(movieId: String): OperationStatus<MovieDetailsDto> {
        return CallHelper.safeApiCall {
            service.getMovieDetails(movieId)
        }
    }

    // Trailer
    override suspend fun getVideoTrailer(movieId: String): OperationStatus<VideosDto> {
        return CallHelper.safeApiCall {
            service.getVideoTrailer(movieId)
        }
    }


    // --------------------------------- ROOM Database ------------------------------------
    // Movie -> MovieDbo (Room save locally)
    override suspend fun saveMovie(movies: Movies): OperationStatus<Unit> {
        return CallHelper.safeRoomCall {
            movieDao.saveToFavorites(
                movie = movies.toMoviesDbo(userEmail),
            )
        }
    }

    override suspend fun deleteMovie(movies: Movies): OperationStatus<Unit> {
        return CallHelper.safeRoomCall {
            movieDao.deleteFromFavorites(movie = movies.toMoviesDbo(userEmail))
        }
    }

    // MovieDbo -> Movie
    override suspend fun getAllSavedMovies(): OperationStatus<List<Movies>> {
        return CallHelper.safeRoomCall {
            movieDao.getAllFavorites(userEmail).map { movieDbo -> movieDbo.toMovies() }
        }
    }

}