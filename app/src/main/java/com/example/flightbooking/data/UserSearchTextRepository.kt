package com.example.flightbooking.data

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserSearchTextRepository(
    private val searchTextDatastore: DataStore<Preferences>
) {
    val searchTextData: Flow<String> = searchTextDatastore.data
        .catch{
            if(it is IOException){
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[SEARCH_TEXT_KEY] ?: ""
        }

    suspend fun setSearchText(searchText: String) {
        searchTextDatastore.edit { preferences ->
            preferences[SEARCH_TEXT_KEY] = searchText
        }
    }
    companion object {
        val SEARCH_TEXT_KEY = stringPreferencesKey("search_text")
    }
}