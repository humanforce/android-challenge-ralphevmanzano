package com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.theme.WeatherTheme
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.WeatherInfo
import com.humanforce.humanforceandroidengineeringchallenge.core.shared.viewmodel.WeatherUiState

@Composable
fun WeatherDetailsSection(
    modifier: Modifier = Modifier,
    uiState: WeatherUiState
) {
    val details = uiState.weatherInfo?.getDetailsList()

    Column(modifier = modifier) {
        if (uiState.isLoading || uiState.weatherInfo == null) {
            WeatherShimmerSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.75f)
            )
        } else {
            if (details != null) {
                for (i in details.chunked(3)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        i.forEach { (title, value) ->
                            WeatherDetailItem(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .background(Color.White, RoundedCornerShape(8.dp))
                                    .padding(16.dp), title = title, value = value
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun WeatherDetailsSectionPreview() {
    WeatherTheme {
        WeatherDetailsSection(
            uiState = WeatherUiState(isLoading = false, weatherInfo = WeatherInfo(
                temp = 22,
                feelsLike = 22,
                tempMin = 22,
                tempMax = 22,
                pressure = 111,
                clouds = 22
            ))
        )
    }
}