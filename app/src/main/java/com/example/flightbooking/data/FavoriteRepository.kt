package com.example.flightbooking.data

import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun insert(favoriteEntry: FavoriteEntry)

    suspend fun delete(favoriteEntry: FavoriteEntry)

    fun favorites(): Flow<List<FavoriteEntry>>
}