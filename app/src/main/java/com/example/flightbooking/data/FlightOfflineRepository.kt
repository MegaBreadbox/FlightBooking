package com.example.flightbooking.data

import kotlinx.coroutines.flow.Flow

class FlightOfflineRepository(private val flightDao: FlightEntryDao): FlightRepository {
    override fun searchFlight(search: String): Flow<List<FlightEntry>> {
        return flightDao.searchFlight(search)
    }

}