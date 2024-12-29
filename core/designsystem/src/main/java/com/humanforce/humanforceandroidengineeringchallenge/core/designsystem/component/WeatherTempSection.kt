package com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.theme.WeatherTheme
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.WeatherInfo
import com.humanforce.humanforceandroidengineeringchallenge.core.shared.viewmodel.WeatherUiState
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun WeatherTempSection(
    modifier: Modifier = Modifier,
    uiState: WeatherUiState
) {
    val weatherInfo = uiState.weatherInfo
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column {
            if (!uiState.isLoading && weatherInfo != null) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    GlideImage(
                        modifier = Modifier.size(72.dp),
                        imageModel = { weatherInfo.weatherIconUrl },
                        imageOptions = ImageOptions(contentScale = ContentScale.Fit)
                    )
                    Text(text = weatherInfo.weatherDescription)
                }
            }

            val tempLabel = if (uiState.isLoading || weatherInfo == null) {
                "--°"
            } else "${weatherInfo.temp}°"
            Text(
                text = tempLabel,
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 96.sp
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val tempMaxLabel = if (uiState.isLoading || weatherInfo == null) {
                "-°C"
            } else "${weatherInfo.tempMax}°C"
            val tempMinLabel = if (uiState.isLoading || weatherInfo == null) {
                "-°C"
            } else "${weatherInfo.tempMin}°C"

            Text(text = tempMaxLabel, fontSize = 32.sp)
            HorizontalDivider(
                modifier = Modifier
                    .width(100.dp)
                    .padding(vertical = 4.dp)
            )
            Text(
                text = tempMinLabel,
                fontSize = 32.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview
@Composable
private fun WeatherTempSectionPreview() {
    WeatherTheme {
        WeatherTempSection(
            modifier = Modifier.fillMaxWidth(),
            uiState = WeatherUiState(
                weatherInfo = WeatherInfo(
                    temp = 22,
                    tempMin = 22,
                    tempMax = 22,
                    weatherDescription = "Sunny"
                )
            )
        )
    }
}