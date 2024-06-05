package com.example.flightbooking.data

import kotlinx.coroutines.flow.Flow

class FlightOfflineRepository(private val flightDao: FlightEntryDao): FlightRepository {
    override fun searchFlight(search: String): Flow<List<airport>> {
        return flightDao.searchFlight(search)
    }

    override suspend fun eligibleFlights(search: String): List<airport> {
        return flightDao.eligibleFlights(search)
    }

}