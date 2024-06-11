package com.example.flightbooking.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteEntryDao {
    @Insert
    suspend fun insert(favorite: favorite)

    @Query("DELETE FROM favorite WHERE departure_code = :departure AND destination_code = :destination")
    suspend fun delete(departure: String, destination: String)

    @Query("SELECT * FROM favorite")
    fun favoritesList(): Flow<List<favorite>>
}