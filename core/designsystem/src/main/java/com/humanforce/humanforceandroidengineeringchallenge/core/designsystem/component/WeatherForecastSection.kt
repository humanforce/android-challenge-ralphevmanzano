package com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.WeatherInfo
import com.humanforce.humanforceandroidengineeringchallenge.core.shared.viewmodel.WeatherUiState

@Composable
fun WeatherForecastSection(
    modifier: Modifier = Modifier,
    uiState: WeatherUiState,
    weatherInfo: WeatherInfo?
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        if (uiState.isLoading || weatherInfo == null) {
            WeatherShimmerSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.75f)
            )
        } else {
            for (forecast in weatherInfo.forecast) {
                ForecastItem(forecast = forecast)
            }
        }
    }
}