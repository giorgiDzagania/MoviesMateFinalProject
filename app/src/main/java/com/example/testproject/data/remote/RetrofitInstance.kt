package com.example.testproject.data.remote

import com.example.testproject.data.remote.service.MoviesService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val moviesService: MoviesService = retrofit.create(MoviesService::class.java)

}