package com.example.flightbooking.screens

import android.util.Log
import android.widget.ImageButton
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.EditCommand
import androidx.compose.ui.text.input.EditProcessor
import androidx.compose.ui.text.input.MoveCursorCommand
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightbooking.R
import com.example.flightbooking.data.airport
import com.example.flightbooking.ui.theme.FlightBookingTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory),
) {
    val flights by searchViewModel.flightsUiState.collectAsState()
    val searchText by searchViewModel.searchText.collectAsState()
    val eligibleFlights by searchViewModel.eligibleFlights.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SearchBar(
            query = searchText,
            onQueryChange = {
                searchViewModel.changeText(it)
            },
            onSearch = {
                coroutineScope.launch {
                    searchViewModel.getEligibleFlights(it)
                    searchViewModel.changeActive(false)
                }

            },
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
                    searchText = searchText,
                    changeText = {
                        searchViewModel.changeText(it)
                    }
                )
            },
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = dimensionResource(R.dimen.big_padding))
        ) {
            if(searchText.isNotEmpty()) {
                SearchList(
                    flightList = flights,
                    onClickSearch = {
                        coroutineScope.launch {
                            searchViewModel.changeText(it)
                            searchViewModel.getEligibleFlights(it)
                            searchViewModel.changeActive(false)
                        }
                    },
                    onClickFill = {
                        searchViewModel.changeText(it)
                    }
                )
            }
        }
        when(flights.map { it.name}.contains(searchText)) {
            false ->
                if(searchText.isEmpty()) {

                } else {
                    NoSearchResults()
                }
            true ->
                EligibleFlightsList(
                    searchedFlight = flights.first(),
                    flightList = eligibleFlights,
                    modifier = Modifier.weight(1F)
                )

        }
    }
}

@Composable
fun NoSearchResults(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()

    ) {
        Text(
            text = stringResource(R.string.no_results_found),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.error,
            modifier = modifier
                .padding(top = dimensionResource(R.dimen.big_padding))
        )
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
fun SearchList(
    flightList: List<airport>,
    onClickSearch: (String) -> Unit,
    onClickFill: (String) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.big_padding)),
        contentPadding = PaddingValues(
            bottom = dimensionResource(R.dimen.big_padding)
        ),
    ) {
        items(
            items = flightList,
            key = { it.id }
        ) {flightEntry ->
            SearchEntry(
                flightEntryText = flightEntry.name,
                flightEntryIdentifier = flightEntry.iata_code,
                onClickSearch = { onClickSearch(it) },
                onClickFill =  { onClickFill(it) }
            )
        }
    }
}

@Composable
fun SearchEntry(
    flightEntryText: String,
    flightEntryIdentifier: String,
    onClickSearch: (String) -> Unit,
    onClickFill: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val iconPadding = Modifier
        .padding(
            start = dimensionResource(R.dimen.medium_padding),
            end = dimensionResource(R.dimen.medium_padding)
        )
    Row() {
        Icon(
            imageVector = Icons.Rounded.Search,
            contentDescription = stringResource(id = R.string.search),
            modifier = iconPadding
                .clickable { onClickSearch(flightEntryText) }
        )
        Text(
            text = flightEntryIdentifier,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = iconPadding
                .clickable { onClickSearch(flightEntryText) }
        )
        Text(
            text = flightEntryText,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier
                .weight(1F)
                .clickable { onClickSearch(flightEntryText) }
        )
        Icon(
            imageVector = Icons.Rounded.Edit,
            contentDescription = stringResource(R.string.enter),
            modifier = iconPadding
                .clickable { onClickFill(flightEntryText) }
        )
    }
}

@Composable
fun EligibleFlightsList(
    searchedFlight: airport,
    flightList: List<airport>,
    modifier: Modifier
){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.big_padding)),
        contentPadding = PaddingValues(
            top = dimensionResource(R.dimen.big_padding),
            bottom = dimensionResource(R.dimen.big_padding),
        ),
        modifier = modifier
    ) {
        items(
            items = flightList,
            key = { it.id }
        ) {
            EligibleFlightEntry(
                eligibleFlights = it,
                searchedFlight = searchedFlight
            )
        }
    }
}

@Composable
fun EligibleFlightEntry(
    eligibleFlights: airport,
    searchedFlight: airport,
    modifier: Modifier = Modifier
){
    val textPadding = Modifier.padding(
        start = dimensionResource(R.dimen.medium_padding),
        end = dimensionResource(R.dimen.medium_padding)
    )
    var favorited by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .fillMaxWidth()
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxSize()
        ) {
            Column(
                modifier = modifier.weight(1F)
            ) {
                Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.medium_padding)))
                Text(
                    text = stringResource(R.string.depart),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = textPadding
                )
                Row {
                    Text(
                        text = searchedFlight.iata_code,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = textPadding
                    )
                    Text(
                        text = searchedFlight.name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = textPadding
                    )
                }
                Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.big_padding)))
                Text(
                    text = stringResource(R.string.arrival),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = textPadding
                )
                Row() {
                    Text(
                        text = eligibleFlights.iata_code,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = textPadding
                    )
                    Text(
                        text = eligibleFlights.name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = textPadding
                    )
                }
                Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.medium_padding)))
            }
            AnimatedContent(
                favorited,
                transitionSpec = {
                    scaleIn() togetherWith (ExitTransition.None)
                },
                label = "favorite"
            ) {
                when(it) {
                    false ->
                        Image(
                            imageVector = Icons.Rounded.FavoriteBorder,
                            contentDescription = stringResource(R.string.favorite),
                            contentScale = ContentScale.FillBounds,
                            alignment = Alignment.Center,
                            modifier = modifier
                                .padding(end = dimensionResource(R.dimen.big_padding))
                                .size(50.dp)
                                .clickable {
                                    favorited = !favorited
                                }
                        )
                    true ->
                        Image(
                            imageVector = Icons.Rounded.Favorite,
                            contentDescription = stringResource(R.string.unfavorite),
                            contentScale = ContentScale.FillBounds,
                            colorFilter = ColorFilter.tint(Color.Red),
                            alignment = Alignment.Center,
                            modifier = modifier
                                .padding(end = dimensionResource(R.dimen.big_padding))
                                .size(50.dp)
                                .clickable {
                                    favorited = !favorited
                                }
                        )
                }
            }
        }
    }
}
@Composable
@Preview(showBackground = true)
fun EligibleFlightPreview() {
    FlightBookingTheme {
        val airport: airport = airport(
            id = 1,
            iata_code = "AAA",
            name = "Airport Name",
            passengers = 100
        )
        EligibleFlightEntry(
            eligibleFlights = airport,
            searchedFlight = airport
        )
    }
}