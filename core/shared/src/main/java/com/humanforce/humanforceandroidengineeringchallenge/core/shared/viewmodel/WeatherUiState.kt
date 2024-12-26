package com.humanforce.humanforceandroidengineeringchallenge.core.shared.viewmodel

import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.WeatherInfo

data class WeatherUiState(
    val isLoading: Boolean = true,
    val weatherInfo: WeatherInfo? = null,
    val error: String? = null,
    val hasNoInternet: Boolean = false,
    val hasNoLocation: Boolean = false,
    val isHome: Boolean = false
)