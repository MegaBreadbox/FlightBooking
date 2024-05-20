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

    @Delete
    suspend fun delete(favorite: favorite)

    @Query("SELECT * FROM favorite")
    fun favoritesList(): Flow<List<favorite>>
}