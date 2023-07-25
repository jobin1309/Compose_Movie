package com.example.movieapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.ui.platform.LocalView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.NetworkResult
import com.example.movieapp.model.MovieResponse
import com.example.movieapp.repo.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.reflect.Array.get
import javax.inject.Inject


@HiltViewModel
class MovieViewmodel @Inject constructor(
    application: Application,
    private var movieRepository: MovieRepository
) : AndroidViewModel(application) {

    private var _movies: MutableLiveData<NetworkResult<MovieResponse>> = MutableLiveData()
    val movies: LiveData<NetworkResult<MovieResponse>> get() = _movies

    init {
        getMovieResponse()
    }

    private fun getMovieResponse() {
        viewModelScope.launch {
            try {
                val response = movieRepository.getMovieResponse(1)
                _movies.value = handleMoviesResponse(response)
            } catch (e: Exception) {
                Log.d("Response", "Error has occured")
            }
        }
    }

    private fun handleMoviesResponse(response: Response<MovieResponse>): NetworkResult<MovieResponse> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }

            response.code() == 402 -> {
                return NetworkResult.Error("API key Limited")
            }

            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes not found")
            }

            response.isSuccessful -> {
                val MovieResponse = response.body()
                Log.d("Response", response.body().toString())
                return NetworkResult.Success(MovieResponse!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }

        }
    }

}