package com.example.flightbooking.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface FlightEntryDao {
    @Query ("SELECT * FROM FlightEntry WHERE name LIKE '%' || :search || '%'" +
            "OR iata_code LIKE '%' || :search || '%'")
    fun searchFlight(search: String): Flow<List<FlightEntry>>
}