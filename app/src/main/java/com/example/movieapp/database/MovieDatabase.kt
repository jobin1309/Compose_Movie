package com.example.movieapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapp.model.Result



@Database(entities = [Result::class],version = 1,  exportSchema = false )
abstract class MovieDatabase: RoomDatabase() {

    abstract fun MovieDao(): MovieDao;

}