package com.example.testproject.data.local.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert()
    suspend fun saveToFavorites(movie: MovieDbo)

    @Delete()
    suspend fun deleteFromFavorites(movie: MovieDbo)

    @Query("SELECT * FROM movies_table WHERE userEmail = :userEmail")
    suspend fun getAllFavorites(userEmail: String): List<MovieDbo>

    /* @Query("SELECT * FROM movies_table")
     suspend fun getAllFavorites(): List<MovieDbo>*/

}