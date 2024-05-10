package com.example.flightbooking.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteEntryDao {
    @Insert
    suspend fun insert(favoriteEntry: FavoriteEntry)

    @Delete
    suspend fun delete(favoriteEntry: FavoriteEntry)

    @Query("SELECT * FROM FavoriteEntry")
    fun favoritesList(): Flow<List<FavoriteEntry>>
}