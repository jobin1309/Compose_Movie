package com.example.movieapp.remote

import com.example.movieapp.model.MovieResponse
import com.example.movieapp.model.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieResponseApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): Response<List<Result>>


    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") api_key: String,
        @Query("query") Query: String
    ): Response<List<Result>>

}

