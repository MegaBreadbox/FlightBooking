package com.example.flightbooking.data

import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun insert(favorite: favorite)

    suspend fun delete(favorite: favorite)

    fun favorites(): Flow<List<favorite>>
}