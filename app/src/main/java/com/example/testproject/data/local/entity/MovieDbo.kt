package com.example.testproject.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_table")
data class MovieDbo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val image: String,
    val title: String,
)