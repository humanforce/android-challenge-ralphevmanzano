package com.humanforce.humanforceandroidengineeringchallenge.feature.cities

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component.NoNetworkBanner
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component.WeatherCitySection
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component.WeatherDetailsSection
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component.WeatherForecastSection
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component.WeatherInfoDivider
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component.WeatherTempSection
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component.WeatherTopAppBar
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.WeatherInfo
import com.humanforce.humanforceandroidengineeringchallenge.core.shared.R

@Composable
fun CityPreviewScreen(
    modifier: Modifier,
    onBackButtonClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    hasNoInternet: Boolean
) {
    val cityPreviewViewModel = hiltViewModel<CityPreviewViewModel>()
    val uiState by cityPreviewViewModel.uiState.collectAsStateWithLifecycle()
    val weatherInfo = uiState.weatherInfo
    val favoriteCities by cityPreviewViewModel.favoriteCities.collectAsStateWithLifecycle()

    fun isFavorite(id: Int): Boolean {
        return favoriteCities.any { it.id == id }
    }

    fun handleFavoriteClick(weatherInfo: WeatherInfo) {
        val city = City(
            id = weatherInfo.cityId,
            name = weatherInfo.cityName,
            lat = weatherInfo.lat,
            long = weatherInfo.long,
            country = weatherInfo.country,
        )

        cityPreviewViewModel.addCityToFavorites(city)
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        WeatherTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            showSearch = false,
            showBackButton = true,
            isFavorite = isFavorite(weatherInfo?.cityId ?: 0),
            showFavorite = !isFavorite(weatherInfo?.cityId ?: 0),
            onFavoriteClick = {
                if (weatherInfo != null) {
                    handleFavoriteClick(weatherInfo)
                }
                onFavoriteClick()
            },
            onBackClick = onBackButtonClick
        )
        if (hasNoInternet) {
            NoNetworkBanner(modifier = Modifier.fillMaxWidth())
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            WeatherCitySection(isCurrentLocation = false, uiState = uiState)
            WeatherTempSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                uiState = uiState
            )
            WeatherInfoDivider(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.details)
            )
            WeatherDetailsSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                uiState = uiState
            )
            WeatherInfoDivider(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.forecast)
            )
            WeatherForecastSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                uiState = uiState
            )
            Spacer(modifier = Modifier.height(56.dp))
        }

        if (uiState.error != null) {
            Toast.makeText(
                LocalContext.current,
                uiState.error ?: stringResource(R.string.unexpected_error),
                Toast.LENGTH_SHORT
            ).show()
            cityPreviewViewModel.consumeError()
        }
    }
}