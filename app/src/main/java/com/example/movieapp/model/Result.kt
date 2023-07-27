package com.example.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName


@Entity(tableName = "movie_table")
data class Result(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String,
)