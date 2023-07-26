package com.example.movieapp.repo

import com.example.movieapp.model.MovieResponse
import com.example.movieapp.model.Result
import com.example.movieapp.remote.MovieResponseApi
import com.example.movieapp.utlis.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MovieRepository @Inject constructor(private val movieResponseApi: MovieResponseApi) {


    suspend fun getMovieResponse(page: Int): Response<MovieResponse> =
        movieResponseApi.getPopularMovies(API_KEY, page)

    suspend fun searchMovieResponse(query: String): Response<MovieResponse> =
        movieResponseApi.searchMovies(API_KEY, query)

}