package com.example.flightbooking.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.flightbooking.ui.theme.FlightBookingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {
    SearchBar(
        query = "",
        onQueryChange = {},
        onSearch = {},
        active = true,
        onActiveChange = {}
    ) {

    }
}

@Composable
@Preview(showBackground = true)
fun SearchScreenPreview() {
    FlightBookingTheme {
        SearchScreen()
    }
}