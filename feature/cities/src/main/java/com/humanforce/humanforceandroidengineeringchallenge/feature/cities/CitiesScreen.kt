package com.humanforce.humanforceandroidengineeringchallenge.feature.cities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component.WeatherTopAppBar
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.theme.LightBlue
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City
import com.humanforce.humanforceandroidengineeringchallenge.core.shared.R
import com.humanforce.humanforceandroidengineeringchallenge.core.shared.viewmodel.WeatherViewModel

@Composable
fun CitiesScreen(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel,
    onSearchClick: () -> Unit,
    onCityClick: (City) -> Unit,
    onClearClick: (City) -> Unit
) {
    val favoriteCities by weatherViewModel.favoriteCities.collectAsStateWithLifecycle()
    val currentLocationWeatherInfo by weatherViewModel.currentLocationWeatherInfo.collectAsStateWithLifecycle()

    Column(modifier = modifier.fillMaxSize()) {
        WeatherTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            onSearchClick = onSearchClick,
            showSearch = true
        )
        if (favoriteCities.isEmpty() && currentLocationWeatherInfo == null) {
            Text(
                stringResource(R.string.add_some_cities_to_your_favorites),
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
                // Add current location item at the top
                if (currentLocationWeatherInfo != null) {
                    val city = City(
                        name = currentLocationWeatherInfo!!.cityName,
                        country = currentLocationWeatherInfo!!.country,
                        lat = currentLocationWeatherInfo!!.lat,
                        long = currentLocationWeatherInfo!!.long,
                    )
                    item {
                        CityItem(
                            modifier = Modifier.fillMaxWidth(),
                            city,
                            true,
                            onCityClick,
                            onClearClick
                        )
                    }
                }
                items(favoriteCities.size) { index ->
                    val city = favoriteCities[index]
                    CityItem(
                        modifier = Modifier.fillMaxWidth(),
                        city,
                        false,
                        onCityClick,
                        onClearClick
                    )
                }
            }
        }
    }
}

@Composable
private fun CityItem(
    modifier: Modifier,
    city: City,
    isCurrentLocation: Boolean,
    onCityClick: (City) -> Unit,
    onClearClick: (City) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = LightBlue),
        modifier = modifier
            .padding(vertical = 8.dp)
            .clickable {
                onCityClick(city)
            },
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            if (isCurrentLocation) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = "Current Location",
                    tint = Color.White
                )
            }
            Text(
                "${city.name}, ${city.country}",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
            if (!isCurrentLocation) {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = "Clear",
                    tint = Color.White,
                    modifier = Modifier.clickable { onClearClick(city) }
                )
            }
        }
    }
}