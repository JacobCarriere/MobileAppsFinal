package com.example.finalproject.model

data class OMDbResponse(
    val Search: List<MovieDto>?,
    val totalResults: String?,
    val Response: String
)

data class MovieDto(
    val imdbID: String,
    val Title: String,
    val Year: String,
    val Poster: String
)
