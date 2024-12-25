package com.humanforce.humanforceandroidengineeringchallenge.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.Forecast
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.WeatherInfo
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
//    val uiState = homeViewModel.uiState.collectAsStateWithLifecycle()
    val weatherInfo = WeatherInfo(
        temp = 25,
        feelsLike = 24.5,
        tempMin = 20,
        tempMax = 28,
        pressure = 1012,
        humidity = 60,
        cityId = 123456,
        cityName = "Sample City",
        windSpeed = 5.5,
        clouds = 75,
        visibility = 10000,
        weatherId = 801,
        weatherMain = "Clouds",
        weatherDescription = "few clouds",
        weatherIconUrl = "https://openweathermap.org/img/wn/02d@2x.png",
        forecast = listOf(
            Forecast(
                dt = 1695564000,
                dtTxt = "Mon",
                tempMin = 18.5,
                tempMax = 25.3,
                weatherMain = "Clouds",
                weatherDescription = "broken clouds",
                weatherIcon = "https://openweathermap.org/img/wn/04d@2x.png"
            ),
            Forecast(
                dt = 1695650400,
                dtTxt = "Tue",
                tempMin = 20.0,
                tempMax = 26.0,
                weatherMain = "Clear",
                weatherDescription = "clear sky",
                weatherIcon = "https://openweathermap.org/img/wn/01d@2x.png"
            ),
            Forecast(
                dt = 1695736800,
                dtTxt = "Wed",
                tempMin = 19.0,
                tempMax = 24.5,
                weatherMain = "Rain",
                weatherDescription = "light rain",
                weatherIcon = "https://openweathermap.org/img/wn/10d@2x.png"
            ),
            Forecast(
                dt = 1695823200,
                dtTxt = "Thu",
                tempMin = 17.5,
                tempMax = 23.0,
                weatherMain = "Clouds",
                weatherDescription = "scattered clouds",
                weatherIcon = "https://openweathermap.org/img/wn/03d@2x.png"
            ),
            Forecast(
                dt = 1695909600,
                dtTxt = "Fri",
                tempMin = 21.0,
                tempMax = 27.0,
                weatherMain = "Clear",
                weatherDescription = "clear sky",
                weatherIcon = "https://openweathermap.org/img/wn/01d@2x.png"
            )
        )
    )

    Column(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Davao City",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Row {
                        GlideImage(
                            modifier = Modifier.size(24.dp),
                            imageModel = { weatherInfo?.weatherIconUrl },
                            imageOptions = ImageOptions(contentScale = ContentScale.Fit)
                        )
                        Text(text = weatherInfo?.weatherDescription ?: "")
                    }
                    Text(
                        text = "${weatherInfo?.temp ?: 0}°",
                        modifier = Modifier.padding(top = 8.dp),
                        fontSize = 96.sp
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "${weatherInfo?.tempMax ?: 0}°C", fontSize = 32.sp)
                    HorizontalDivider(modifier = Modifier.width(100.dp))
                    Text(text = "${weatherInfo?.tempMin ?: 0}°C", fontSize = 32.sp)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(16.dp))
                Text("Details")
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                )
            }
            val details = listOf(
                "Feels Like" to "${weatherInfo?.feelsLike ?: 0}°",
                "Humidity" to "${weatherInfo?.humidity ?: 0}%",
                "Wind Speed" to "${weatherInfo?.windSpeed ?: 0} m/s",
                "Pressure" to "${weatherInfo?.pressure ?: 0} hPa",
                "Visibility" to "${weatherInfo?.visibility ?: 0} m",
                "Clouds" to "${weatherInfo?.clouds} %"
            )
            Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                for (i in details.chunked(3)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        i.forEach { (title, value) ->
                            Column(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(Color.White, RoundedCornerShape(8.dp))
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
                                    color = Color.Gray,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = value,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                                    color = Color.Black,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(16.dp))
                Text("Forecast")
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .background(color = Color.DarkGray),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                weatherInfo.forecast.map { 
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(it.dtTxt)
                        GlideImage(
                            modifier = Modifier.size(24.dp),
                            imageModel = { it.weatherIcon },
                            imageOptions = ImageOptions(contentScale = ContentScale.Fit)
                        )
                        Text("${it.tempMax.toInt()}°")
                        HorizontalDivider(modifier = Modifier.width(24.dp))
                        Text("${it.tempMin.toInt()}°")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(modifier = Modifier.fillMaxSize())
    }
}