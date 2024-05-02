package com.example.flightbooking.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val departure_code: String,
    val destination_code: String
)
