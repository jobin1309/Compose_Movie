package com.example.movieapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapp.NetworkResult
import com.example.movieapp.model.MovieResponse
import com.example.movieapp.model.Result
import com.example.movieapp.repo.MovieRepository
import com.example.movieapp.utlis.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    application: Application,
    private var movieRepository: MovieRepository
) : AndroidViewModel(application) {

    private var _movies: MutableLiveData<NetworkResult<MovieResponse>> = MutableLiveData()

    val movies: LiveData<NetworkResult<MovieResponse>> get() = _movies

    val roomMovies: LiveData<List<Result>> = movieRepository.readAllMovie().asLiveData()


    init {
        fetchAndCachePopularMovies()
    }


     fun getMovieResponse() {
        viewModelScope.launch {
            try {
                val response = handleMoviesResponse(movieRepository.getMovieResponse(1))
                _movies.value = response
                Log.d("Movies", _movies.toString())
            } catch (e: Exception) {
                Log.d("Response", e.message.toString())
            }
        }
    }

    private fun fetchAndCachePopularMovies() {
        viewModelScope.launch {
            val response = movieRepository.getMovieResponse(1)
            if (response.isSuccessful) {
                val movieResponse = response.body()
                movieResponse?.results.let { movieList ->
                    val movieModels = movieList?.map { movieResult ->
                        Result(
                            id = movieResult.id,
                            title = movieResult.title,
                            posterPath = Constants.POSTER_BASE_URL + movieResult.posterPath,
                            releaseDate = movieResult.releaseDate
                        )
                    }
                    movieModels?.forEach() {

                        Log.d("Database insertion", movieModels.toString())
                        movieRepository.insertMovie(it)
                    }

                }
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