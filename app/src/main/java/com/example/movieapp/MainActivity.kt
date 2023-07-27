package com.example.movieapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.movieapp.model.MovieResponse
import com.example.movieapp.model.Result
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.utlis.Constants
import com.example.movieapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            MovieAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    MovieScreen(viewModel)
                }
            }
        }
    }

    @Composable
    fun MovieCard(movieResult: Result) {
        val imagePainter = rememberAsyncImagePainter(
            ImageRequest.Builder
                (LocalContext.current)
                .data(data = Constants.POSTER_BASE_URL + movieResult.posterPath)
                .apply(block = fun ImageRequest.Builder.() {
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_background)
                    error(com.google.android.material.R.drawable.mtrl_ic_error)
                }).build()
        )

        Card(
            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp),
            shape = RoundedCornerShape(5.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {
            Column() {
                Image(
                    painter = imagePainter,
                    contentDescription = "poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
                Column(Modifier.padding(10.dp)) {
                    Text(
                        text = movieResult.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                    Text(
                        text = movieResult.releaseDate,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                }
            }
        }
    }

    @Composable
    fun moviePage(viewModel: MovieViewModel) {

        val movieState by viewModel.movies.observeAsState()
//        val movieDatabaseState by viewModel.room_movies.observeAsState()

        LazyColumn() {


            when (movieState) {
                is NetworkResult.Loading<*> -> {
                    Log.d("MovieState", "MovieState is loading")
                }

                is NetworkResult.Success<*> -> {
                    val movieResponse = (movieState as NetworkResult.Success<MovieResponse>).data
                    if (movieResponse != null) {
                        items(movieResponse.results) { movie ->
                            MovieCard(movie)
                        }
                    }
                    Log.d("MovieState", "Movie response is SuccessFull")
                }

                else -> {}

            }
        }
        Log.d("MoviesFetch", viewModel.movies.toString())

    }

    @Composable
    fun MovieScreen(viewModel: MovieViewModel) {
        val movieRoomState by viewModel.roomMovies.observeAsState()

        LazyColumn() {
            items(movieRoomState ?: emptyList()) { movieResult ->
                MovieCard(movieResult)
            }
        }

    }


    @Composable
    @Preview(showBackground = true)
    fun movieRowPreview() {

    }
}





