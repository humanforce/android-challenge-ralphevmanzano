package com.humanforce.humanforceandroidengineeringchallenge.feature.home

import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.WeatherInfo

data class HomeUiState(
    val isLoading: Boolean = false,
    val weatherInfo: WeatherInfo? = null,
    val error: String? = null
)
