package com.example.finalproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.finalproject.ui.screens.checkout.CheckoutScreen
import com.example.finalproject.ui.screens.checkout.ThankYouScreen
import com.example.finalproject.ui.screens.home.HomeScreen
import com.example.finalproject.ui.screens.locations.LocationsScreen
import com.example.finalproject.ui.screens.movies.MovieDetailScreen
import com.example.finalproject.ui.screens.movies.MoviesScreen
import com.example.finalproject.ui.screens.snacks.SnacksScreen
import com.example.finalproject.viewmodel.AppViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    appViewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Movies.route) {
            MoviesScreen(navController, appViewModel)
        }
        composable(Screen.MovieDetail.route) {
            MovieDetailScreen(navController, appViewModel)
        }
        composable(Screen.Snacks.route) {
            SnacksScreen(navController, appViewModel)
        }
        composable(Screen.Locations.route) {
            LocationsScreen(navController)
        }
        composable(Screen.Checkout.route) {
            CheckoutScreen(navController, appViewModel)
        }
        composable(Screen.ThankYou.route) {
            ThankYouScreen(navController)
        }
    }
}
