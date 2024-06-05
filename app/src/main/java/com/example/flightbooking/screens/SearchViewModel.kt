package com.example.flightbooking.screens

import androidx.compose.runtime.collectAsState
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.update

class SearchViewModel(
    private val flightRepository: FlightRepository,
    private val favoriteRepository: FavoriteRepository
): ViewModel() {

    var searchActive by mutableStateOf(false)
        private set

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _eligibleFlights = MutableStateFlow<List<airport>>(emptyList())
    val eligibleFlights = _eligibleFlights.asStateFlow()


    @OptIn(ExperimentalCoroutinesApi::class)
    val flightsUiState = searchText
        .flatMapLatest {searchText ->
            flightRepository.searchFlight(searchText)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_DELAY),
            initialValue = emptyList()
        )

    suspend fun getEligibleFlights(searchFlight: String) {
        _eligibleFlights.update { flightRepository.eligibleFlights(searchFlight) }
    }

    fun changeText(input: String) {
        _searchText.update { input }
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