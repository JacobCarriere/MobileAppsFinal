package com.example.finalproject.data.snacks

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "snacks",
    indices = [Index(value = ["name"], unique = true)]
)
data class SnackEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val price: Double,
    val amountInCart: Int = 0
)
