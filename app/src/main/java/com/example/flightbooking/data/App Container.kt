package com.example.flightbooking.data

import android.content.Context

interface AppContainer {
    val favoriteRepository: FavoriteRepository
    val flightRepository: FlightRepository
}

class DefaultAppContainer(private val context: Context): AppContainer {
    override val favoriteRepository: FavoriteRepository by lazy {
        FavoriteOfflineRepository(FlightDatabase.getDatabase(context).favoriteEntryDao())
    }
    override val flightRepository: FlightRepository by lazy {
        FlightOfflineRepository(FlightDatabase.getDatabase(context).flightEntryDao())
    }

}