package com.humanforce.humanforceandroidengineeringchallenge.core.shared.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.WeatherInfo
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.usecase.GetCurrentWeatherAndForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCurrentWeatherAndForecastUseCase: GetCurrentWeatherAndForecastUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun getCurrentWeatherAndForecast(lat: Double, long: Double, isHome: Boolean = false) {
        viewModelScope.launch {
            getCurrentWeatherAndForecastUseCase(
                lat,
                long,
                onStart = { _uiState.update { it.copy(isLoading = true) } },
                onComplete = {},
                onError = { error -> _uiState.update { it.copy(isLoading = false, error = error) } }
            ).collect { weatherInfo ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        weatherInfo = weatherInfo,
                        isHome = isHome
                    )
                }
            }
        }
    }
}