package com.example.testproject.data.local.entity

import android.graphics.Movie
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "movies_table")
data class MovieDbo(
    @PrimaryKey()
    val id: Int = 0,
    val image: String,
    val title: String,
    val userEmail: String
)

/*

@Entity(
    tableName = "saved_movie",
    primaryKeys =["movieId", "userEmail"],
    ForeignKey(
        entity = Movie::class,
        parentColumns = ["id"],
        childColumns = ["movieId"],
        onDelete = CASCADE
    )

)

data class SavedMovie(
    val movieId:Int,
    val userEmail: String
)
*/
