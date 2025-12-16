package com.example.finalproject.model

data class Snack(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val amountInCart: Int = 0
)
