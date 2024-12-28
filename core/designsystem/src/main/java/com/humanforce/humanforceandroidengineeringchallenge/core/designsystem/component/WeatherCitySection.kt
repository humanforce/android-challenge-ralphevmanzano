package com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.humanforce.humanforceandroidengineeringchallenge.core.shared.viewmodel.WeatherUiState

@Composable
fun WeatherCitySection(
    modifier: Modifier = Modifier,
    isCurrentLocation: Boolean,
    uiState: WeatherUiState,
) {
    val weatherInfo = uiState.weatherInfo
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (!uiState.isLoading && weatherInfo != null) {
            if (isCurrentLocation) {
                Icon(Icons.Default.LocationOn, contentDescription = "Current Location")
            }
            Text(
                text = weatherInfo.cityName,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )
        } else {
            WeatherShimmerSection(modifier = Modifier.size(width = 160.dp, height = 56.dp))
        }
    }
}