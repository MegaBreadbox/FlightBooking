package com.example.flightbooking.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightbooking.FlightApplication
import com.example.flightbooking.data.FavoriteRepository
import com.example.flightbooking.data.airport
import com.example.flightbooking.data.FlightRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SearchViewModel(
    private val flightRepository: FlightRepository,
    private val favoriteRepository: FavoriteRepository
): ViewModel() {
    var searchText by mutableStateOf("")
        private set

    var searchActive by mutableStateOf(false)
        private set

    val flightsUiState: StateFlow<List<airport>> = flightRepository.searchFlight(searchText).map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_DELAY),
            initialValue = listOf()
        )

    fun changeText(input: String) {
        searchText = input
    }

    fun changeActive(input: Boolean) {
        searchActive = input
    }

    companion object {
        private const val TIMEOUT_DELAY = 5000L

        val Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightApplication)
                val flightRepository = application.container.flightRepository
                val favoriteRepository = application.container.favoriteRepository
                SearchViewModel(
                    flightRepository = flightRepository,
                    favoriteRepository = favoriteRepository
                )
            }
        }
    }
}