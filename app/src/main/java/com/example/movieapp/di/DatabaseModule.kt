package com.example.movieapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapp.database.MovieDao
import com.example.movieapp.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideDatabaseInstance(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context, MovieDatabase::class.java, "Movie_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.MovieDao();
    }

}