package com.example.movieapp
import android.net.Uri
import android.os.Bundle
import android.provider.SyncStateContract.Columns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.movieapp.model.MovieResponse
import com.example.movieapp.model.Result
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.viewmodel.MovieViewmodel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            MovieAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    moviePage()
                }
            }
        }
    }

    @Composable
    fun movieRow(movieResult: Result) {

//        val viewModel: MovieViewmodel by viewModels()
//        viewModel.getMovieResponse()


        Row(Modifier.padding(10.dp)) {

            Image(
                painter = rememberAsyncImagePainter(
                    movieResult.poster_path // or ht
                ),
                contentDescription = "123",
                modifier = Modifier
                    .fillMaxSize()
                    .height(200.dp)
                    .width(300.dp),
                contentScale = ContentScale.FillWidth
            )

            Spacer(modifier = Modifier.padding(10.dp))

            Column(Modifier.padding(7.dp)) {
                Text(text = movieResult.original_title)
                Text(text = movieResult.release_date)
                Text(text = movieResult.vote_average.toString())
            }

        }

    }

    @Composable
    fun moviePage() {

        val mViewModel: MovieViewmodel by viewModels()

        // State
        val movies = mViewModel.movies.observeAsState()

        // API call

        LazyColumn() {

        }

    }

}

