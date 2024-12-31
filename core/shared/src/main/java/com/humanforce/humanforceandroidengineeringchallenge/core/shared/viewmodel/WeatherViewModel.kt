package com.humanforce.humanforceandroidengineeringchallenge.core.shared.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.WeatherInfo
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.usecase.GetCurrentWeatherAndForecastUseCase
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.usecase.LoadFavoriteCitiesUseCase
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.usecase.RemoveFavoriteCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getCurrentWeatherAndForecastUseCase: GetCurrentWeatherAndForecastUseCase,
    private val removeFavoriteCityUseCase: RemoveFavoriteCityUseCase,
    private val loadFavoriteCitiesUseCase: LoadFavoriteCitiesUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private val _currentLocationWeatherInfo = MutableStateFlow<WeatherInfo?>(null)
    val currentLocationWeatherInfo: StateFlow<WeatherInfo?> = _currentLocationWeatherInfo.asStateFlow()

    private var prevLat: Double? = null
    private var prevLong: Double? = null
    private var prevIsHome: Boolean = false

    fun getCurrentWeatherAndForecast(lat: Double, long: Double, isHome: Boolean = false) {
        prevLat = lat
        prevLong = long
        prevIsHome = isHome

        viewModelScope.launch {
            getCurrentWeatherAndForecastUseCase(
                lat = lat,
                long = long,
                onStart = { _uiState.update { it.copy(isLoading = true, error = null) } },
                onError = { error -> _uiState.update { it.copy(isLoading = false, error = error) } }
            ).collect { weatherInfo ->
                if (isHome) {
                    _currentLocationWeatherInfo.value = weatherInfo
                }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        weatherInfo = weatherInfo,
                        isCurrentLocation = isHome,
                        error = null
                    )
                }
            }
        }
    }

    val favoriteCities: StateFlow<List<City>> = loadFavoriteCitiesUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun removeCityToFavorites(city: City) {
        viewModelScope.launch {
            // if selected city is removed from favorites, show the current location weather info
            if (_uiState.value.weatherInfo?.cityId == city.id) {
                _uiState.update { it.copy(weatherInfo = currentLocationWeatherInfo.value) }
            }
            removeFavoriteCityUseCase(city)
        }
    }

    fun onNetworkConnectionStateChange(enabled: Boolean) {
        val prev = _uiState.value.hasNoInternet
        _uiState.update { it.copy(hasNoInternet = !enabled) }
        val hasChangedToEnabled = enabled && prev != _uiState.value.hasNoInternet
        // Fetch weather info from last coordinates
        if (hasChangedToEnabled && prevLat != null && prevLong != null) {
            getCurrentWeatherAndForecast(prevLat!!, prevLong!!, prevIsHome)
        }
    }

    fun consumeError() {
        _uiState.update { it.copy(error = null) }
    }

    fun onLocationPermissionDenied() {
        _uiState.update { it.copy(hasNoLocationPermission = true) }
    }

    fun resetPermissionFlag() {
        _uiState.update { it.copy(hasNoLocationPermission = false) }
    }

    fun onLocationServicesEnabled() {
        _uiState.update { it.copy(hasNoLocationServices = false) }
    }

    fun onLocationServicesDisabled() {
        _uiState.update { it.copy(hasNoLocationServices = true) }
    }
}