package com.example.movieapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movieapp.model.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert
    suspend fun insert(result: Result) {
    }
     @Query("SELECT * FROM movie_table")
    fun readAllData(): Flow<List<Result>>
}