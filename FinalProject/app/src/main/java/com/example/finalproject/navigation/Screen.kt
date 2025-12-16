package com.example.finalproject.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Movies : Screen("movies")
    object MovieDetail : Screen("movie_detail")
    object Snacks : Screen("snacks")
    object Locations : Screen("locations")
    object Checkout : Screen("checkout")
    object ThankYou : Screen("thank_you")
}
