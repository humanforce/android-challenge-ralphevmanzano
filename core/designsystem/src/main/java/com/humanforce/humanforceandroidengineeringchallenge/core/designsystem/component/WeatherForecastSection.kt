package com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.theme.WeatherTheme
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.Forecast
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.WeatherInfo
import com.humanforce.humanforceandroidengineeringchallenge.core.shared.viewmodel.WeatherUiState

@Composable
fun WeatherForecastSection(
    modifier: Modifier = Modifier,
    uiState: WeatherUiState,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        if (uiState.isLoading || uiState.weatherInfo == null) {
            WeatherShimmerSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.75f)
            )
        } else {
            for (forecast in uiState.weatherInfo!!.forecast) {
                ForecastItem(forecast = forecast)
            }
        }
    }
}

@Preview
@Composable
private fun WeatherForecastSectionPreview() {
    WeatherTheme {
        WeatherForecastSection(
            uiState = WeatherUiState(
                isLoading = false, weatherInfo = WeatherInfo(
                    forecast = listOf(
                        Forecast(
                            day = "Mon",
                            tempMax = 25.0,
                            tempMin = 20.0,
                            weatherDescription = "Sunny",
                            weatherIconUrl = ""
                        )
                    )
                )
            ),
        )
    }
}