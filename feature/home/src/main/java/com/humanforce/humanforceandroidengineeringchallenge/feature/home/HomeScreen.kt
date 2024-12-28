package com.humanforce.humanforceandroidengineeringchallenge.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component.WeatherCitySection
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component.WeatherDetailsSection
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component.WeatherForecastSection
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component.WeatherInfoDivider
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component.WeatherTempSection
import com.humanforce.humanforceandroidengineeringchallenge.core.designsystem.component.WeatherTopAppBar
import com.humanforce.humanforceandroidengineeringchallenge.core.shared.R
import com.humanforce.humanforceandroidengineeringchallenge.core.shared.viewmodel.WeatherViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel,
    onSearchClick: () -> Unit
) {

    val uiState by weatherViewModel.uiState.collectAsStateWithLifecycle()
    val currentLocationWeatherInfo by weatherViewModel.currentLocationWeatherInfo.collectAsStateWithLifecycle()

    val weatherInfo = uiState.weatherInfo
    val isCurrentLocation = weatherInfo?.cityId == currentLocationWeatherInfo?.cityId
    val details = weatherInfo?.getDetailsList()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        WeatherTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            showSearch = true,
            onSearchClick = onSearchClick,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            WeatherCitySection(isCurrentLocation = isCurrentLocation, uiState = uiState)
            WeatherTempSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                uiState = uiState,
                weatherInfo = weatherInfo
            )
            WeatherInfoDivider(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.details)
            )
            WeatherDetailsSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                details = details,
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
                uiState = uiState,
                weatherInfo = weatherInfo
            )
        }
    }
}