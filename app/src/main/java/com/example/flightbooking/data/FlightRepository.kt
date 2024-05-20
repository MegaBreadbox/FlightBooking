package com.example.flightbooking.data

import kotlinx.coroutines.flow.Flow

interface FlightRepository {
    fun searchFlight(search: String): Flow<List<airport>>
}