package com.example.flightbooking.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FlightEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val iata_code: String,
    val name: String,
    val passengers: Int
)