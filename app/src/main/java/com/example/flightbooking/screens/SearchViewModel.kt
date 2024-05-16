package com.example.flightbooking.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SearchViewModel: ViewModel() {
    var searchText by mutableStateOf("")
        private set

    var searchActive by mutableStateOf(false)
        private set

    fun changeText(input: String) {
        searchText = input
    }

    fun changeActive(input: Boolean) {
        searchActive = input
    }
}