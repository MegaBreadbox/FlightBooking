package com.example.flightbooking.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

interface AppContainer {
    val favoriteRepository: FavoriteRepository
    val flightRepository: FlightRepository
    val userSearchTextRepository: UserSearchTextRepository
}

class DefaultAppContainer(private val context: Context): AppContainer {

    private val SEARCH_TEXT_PREFERENCES_NAME = "user_search_text_preferences"

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = SEARCH_TEXT_PREFERENCES_NAME
    )
    override val favoriteRepository: FavoriteRepository by lazy {
        FavoriteOfflineRepository(FlightDatabase.getDatabase(context).favoriteEntryDao())
    }
    override val flightRepository: FlightRepository by lazy {
        FlightOfflineRepository(FlightDatabase.getDatabase(context).flightEntryDao())
    }
    override val userSearchTextRepository: UserSearchTextRepository by lazy {
        UserSearchTextRepository(context.dataStore)
    }


}