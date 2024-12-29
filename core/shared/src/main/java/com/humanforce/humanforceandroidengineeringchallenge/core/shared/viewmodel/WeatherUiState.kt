package com.humanforce.humanforceandroidengineeringchallenge.core.shared.viewmodel

import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.WeatherInfo

data class WeatherUiState(
    val isLoading: Boolean = false,
    val weatherInfo: WeatherInfo? = null,
    val isCurrentLocation: Boolean = false,
    val error: String? = null,
    val hasNoInternet: Boolean = false,
    val hasNoLocationPermission: Boolean = false,
    val hasNoLocationServices: Boolean = false
)