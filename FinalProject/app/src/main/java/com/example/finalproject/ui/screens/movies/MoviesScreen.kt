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
            Movie("1", "Batman Begins", "2005", "https://via.placeholder.com/150"),
            Movie("2", "The Dark Knight", "2008", "https://via.placeholder.com/150"),
            Movie("3", "The Dark Knight Rises", "2012", "https://via.placeholder.com/150")
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
