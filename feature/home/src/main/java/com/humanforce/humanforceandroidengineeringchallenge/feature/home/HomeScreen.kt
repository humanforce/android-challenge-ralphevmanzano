package com.humanforce.humanforceandroidengineeringchallenge.feature.home

import android.util.Log
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component.WeatherTopAppBar
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City
import com.humanforce.humanforceandroidengineeringchallenge.core.shared.viewmodel.WeatherViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel,
    onSearchClick: () -> Unit
) {

    val uiState by weatherViewModel.uiState.collectAsStateWithLifecycle()
    val weatherInfo = uiState.weatherInfo
    val isCurrentLocation = uiState.isCurrentLocation

    val favoriteCities by weatherViewModel.favoriteCities.collectAsStateWithLifecycle()

    fun isFavorite(id: Int): Boolean {
        return favoriteCities.any { it.id == id }
    }

    fun handleFavoriteClick() {
        val city = City(
            id = weatherInfo?.cityId ?: 0,
            name = weatherInfo?.cityName ?: "",
            lat = weatherInfo?.lat ?: 0.0,
            long = weatherInfo?.long ?: 0.0,
            country = weatherInfo?.country ?: "",
        )

        if (isFavorite(city.id)) {
            weatherViewModel.removeCityToFavorites(city)
        } else {
            weatherViewModel.addCityToFavorites(city)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        WeatherTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            onSearchClick = onSearchClick,
            isFavorite = isFavorite(weatherInfo?.cityId ?: 0),
            onFavoriteClick = { if (!isCurrentLocation) handleFavoriteClick() }
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = weatherInfo?.cityName.orEmpty(),
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
            )
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Row(verticalAlignment = Alignment.Bottom) {
                        GlideImage(
                            modifier = Modifier.size(72.dp),
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
                    HorizontalDivider(modifier = Modifier
                        .width(100.dp)
                        .padding(vertical = 4.dp))
                    Text(text = "${weatherInfo?.tempMin ?: 0}°C", fontSize = 32.sp, color = Color.Gray)
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
                "Clouds" to "${weatherInfo?.clouds}%"
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
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
                                    .padding(16.dp),
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
                    Spacer(modifier = Modifier.height(16.dp))
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
                    .background(Color.Transparent, RoundedCornerShape(8.dp)),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                if (weatherInfo != null) {
                    for (forecast in weatherInfo.forecast) {
                        Column(
                            modifier = Modifier.padding(vertical = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(forecast.day)
                            GlideImage(
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(vertical = 8.dp),
                                imageModel = { forecast.weatherIconUrl },
                                imageOptions = ImageOptions(contentScale = ContentScale.Fit)
                            )
                            Text("${forecast.tempMax.toInt()}°")
                            HorizontalDivider(modifier = Modifier
                                .width(48.dp)
                                .padding(vertical = 4.dp))
                            Text("${forecast.tempMin.toInt()}°", color = Color.Gray)
                        }
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
//        HomeScreen(modifier = Modifier.fillMaxSize())
    }
}