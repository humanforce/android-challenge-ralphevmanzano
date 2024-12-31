package com.humanforce.humanforceandroidengineeringchallenge.feature.search

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component.NoNetworkBanner
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City
import com.humanforce.humanforceandroidengineeringchallenge.core.shared.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onCityClicked: (City) -> Unit,
    onNavigateBack: () -> Unit,
    hasNoInternet: Boolean
) {
    val searchViewModel: SearchViewModel = hiltViewModel()
    val uiState by searchViewModel.uiState.collectAsStateWithLifecycle()
    val cities = uiState.cities

    var text by rememberSaveable { mutableStateOf("") }


    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            modifier = Modifier.padding(horizontal = 16.dp),
            navigationIcon = {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.clickable { onNavigateBack() }
                )
            },
            title = {
                SearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    query = text,
                    onQueryChange = {
                        text = it
                        searchViewModel.onSearchChanged(text)
                    },
                    onSearch = { searchViewModel.onSearchChanged(text) },
                    placeholder = { Text(stringResource(R.string.search_cities)) },
                    trailingIcon = {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.Black
                            )
                        } else {
                            if (text.isNotBlank()) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null,
                                    modifier = Modifier.clickable {
                                        text = ""
                                        searchViewModel.onSearchChanged(text)
                                    }
                                )
                            }
                        }
                    },
                    active = false,
                    onActiveChange = {},
                    content = {}
                )
            }
        )

        if (hasNoInternet) {
            NoNetworkBanner(modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
        }

        if (cities.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(cities.size) { index ->
                    val city = cities[index]
                    SearchItem(
                        modifier = Modifier.clickable { onCityClicked(city) },
                        city = city
                    )
                }
            }
        } else {
            Text(
                stringResource(R.string.no_cities_found),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            )
        }

        if (uiState.error != null) {
            Toast.makeText(
                LocalContext.current,
                uiState.error ?: stringResource(R.string.unexpected_error),
                Toast.LENGTH_SHORT
            ).show()
            searchViewModel.consumeError()
        }
    }
}

@Composable
fun SearchItem(modifier: Modifier = Modifier, city: City) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            "${city.name}, ${city.country}",
            modifier = Modifier
                .padding(vertical = 8.dp),
            style = MaterialTheme.typography.bodyLarge
        )

        HorizontalDivider(thickness = 1.dp)
    }
}