package com.example.flightbooking.data

import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun insert(favorite: favorite)

    suspend fun delete(departure: String, destination: String)

    fun favorites(): Flow<List<favorite>>
}