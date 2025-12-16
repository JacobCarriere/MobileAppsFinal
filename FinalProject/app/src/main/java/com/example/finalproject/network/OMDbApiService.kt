package com.example.finalproject.network

import com.example.finalproject.model.OMDbResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDbApiService {
    @GET("/")
    suspend fun searchMovies(
        @Query("apikey") apiKey: String,
        @Query("s") search: String
    ): OMDbResponse
}
