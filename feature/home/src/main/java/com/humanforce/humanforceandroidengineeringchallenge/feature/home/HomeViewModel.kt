package com.humanforce.humanforceandroidengineeringchallenge.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.usecase.GetCurrentWeatherAndForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentWeatherAndForecastUseCase: GetCurrentWeatherAndForecastUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getCurrentWeatherAndForecast(7.0736, 125.6110)
    }

    fun getCurrentWeatherAndForecast(lat: Double, long: Double) {
        viewModelScope.launch {
            getCurrentWeatherAndForecastUseCase(
                lat,
                long,
                onStart = { _uiState.value = _uiState.value.copy(isLoading = true) },
                onComplete = {},
                onError = { _uiState.value = _uiState.value.copy(isLoading = false, error = it) }
            ).collect {
                _uiState.value = _uiState.value.copy(isLoading = false, weatherInfo = it)
            }
        }
    }
}