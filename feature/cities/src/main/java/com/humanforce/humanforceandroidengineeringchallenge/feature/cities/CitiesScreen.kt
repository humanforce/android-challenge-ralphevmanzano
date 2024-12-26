package com.humanforce.humanforceandroidengineeringchallenge.feature.cities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component.WeatherTopAppBar
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City
import com.humanforce.humanforceandroidengineeringchallenge.core.shared.viewmodel.WeatherViewModel

@Composable
fun CitiesScreen(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel,
    onSearchClick: () -> Unit,
    onCityClick: (City) -> Unit
) {
    val favoriteCities by weatherViewModel.favoriteCities.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {
        WeatherTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            onSearchClick = onSearchClick,
        )
        if (favoriteCities.isEmpty()) {
            Text(
                "Add some cities to your favorites!",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
                items(favoriteCities.size) { index ->
                    ListItem(
                        headlineContent = {
                            Text(favoriteCities[index].name, style = MaterialTheme.typography.titleMedium)
                        },
                        modifier = Modifier.clickable {
                            onCityClick(favoriteCities[index])
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CitiesScreenPreview() {
}