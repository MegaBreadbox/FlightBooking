package com.example.flightbooking.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightbooking.R
import com.example.flightbooking.ui.theme.FlightBookingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = viewModel(),
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SearchBar(
            query = searchViewModel.searchText,
            onQueryChange = { searchViewModel.changeText(it) },
            onSearch = {},
            active = searchViewModel.searchActive,
            onActiveChange = { searchViewModel.changeActive(it) },
            placeholder = { Text(text = stringResource(R.string.search)) },
            leadingIcon = {
                SearchLeadingIcon(
                    searchActive = searchViewModel.searchActive,
                    changeActive = { searchViewModel.changeActive(false) }
                )
            },
            trailingIcon = {
                SearchTrailingIcon(
                    searchText = searchViewModel.searchText,
                    changeText = { searchViewModel.changeText(it) }
                )
            },
            modifier = modifier.align(Alignment.CenterHorizontally)
        ) {

        }
    }
}

@Composable
fun SearchLeadingIcon(
    searchActive: Boolean,
    changeActive: () -> Unit,
    modifier: Modifier = Modifier
){
    if (searchActive) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            contentDescription = stringResource(R.string.back_button),
            modifier = modifier.clickable {
                changeActive()
            }
        )
    }
    else {
        Icon(
            imageVector = Icons.Rounded.Search,
            contentDescription = stringResource(id = R.string.search)
        )
    }
}

@Composable
fun SearchTrailingIcon(
    searchText: String,
    changeText: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if(searchText.isNotEmpty()) {
        Icon(
            imageVector = Icons.Rounded.Clear,
            contentDescription = stringResource(R.string.clear),
            modifier = modifier.clickable {
                changeText("")
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SearchScreenPreview() {
    FlightBookingTheme {
        SearchScreen()
    }
}