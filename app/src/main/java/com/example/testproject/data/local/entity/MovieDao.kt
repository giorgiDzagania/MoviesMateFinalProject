package com.example.testproject.data.local.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveToFavorites(movie: MovieDbo)

    @Delete()
    suspend fun deleteFromFavorites(movie: MovieDbo)

    @Query("SELECT * FROM movies_table")
    suspend fun getAllFavorites(): List<MovieDbo>

}