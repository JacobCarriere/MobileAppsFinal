package com.example.finalproject.ui.screens.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.finalproject.R
import com.example.finalproject.model.Movie
import com.example.finalproject.viewmodel.AppViewModel

@Composable
fun MoviesScreen(navController: NavController, appViewModel: AppViewModel) {
    val context = LocalContext.current

    // Placeholder movie list
    val movies = remember {
        mutableStateListOf(
            Movie("1", "Heathers", "1989", "https://a.ltrbxd.com/resized/film-poster/5/0/1/7/7/50177-heathers-0-2000-0-3000-crop.jpg?v=dbd1f7062c"),
            Movie("2", "Scott Pilgrim vs. the World", "2010", "https://a.ltrbxd.com/resized/sm/upload/vs/75/02/fx/2B5zjs5E3xerqAyowpw3QcOCyLq-0-2000-0-3000-crop.jpg?v=3aef2095df"),
            Movie("3", "Whiplash", "2014", "https://a.ltrbxd.com/resized/sm/upload/cl/dn/kr/f1/4C9LHDxMsoYI0S3iMPZdm3Oevwo-0-2000-0-3000-crop.jpg?v=d13ea36528"),
            Movie("4", "La La Land", "2016", "https://a.ltrbxd.com/resized/film-poster/2/4/0/3/4/4/240344-la-la-land-0-2000-0-3000-crop.jpg?v=053670ff84"),
            Movie("5", "The Nice Guys", "2016", "https://a.ltrbxd.com/resized/film-poster/2/1/6/3/0/1/216301-the-nice-guys-0-2000-0-3000-crop.jpg?v=40616daa2c"),
            Movie("6", "Your Name.", "2016", "https://a.ltrbxd.com/resized/sm/upload/md/ku/l7/m5/xq1Ugd62d23K2knRUx6xxuALTZB-0-2000-0-3000-crop.jpg?v=443be7928f"),
            Movie("7", "Knives Out", "2017", "https://a.ltrbxd.com/resized/film-poster/4/7/5/3/7/0/475370-knives-out-0-2000-0-3000-crop.jpg?v=7da76d742c"),
            )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = context.getString(R.string.screen_movies),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(movies, key = { it.id }) { movie ->
                MovieListItem(movie = movie) {
                    appViewModel.selectMovie(movie, "")
                    navController.navigate("movie_detail")
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun MovieListItem(movie: Movie, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(movie.posterUrl),
            contentDescription = movie.title,
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = "${movie.title} (${movie.year})",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
