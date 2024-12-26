package com.humanforce.humanforceandroidengineeringchallenge.core.shared.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.usecase.AddFavoriteCityUseCase
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
    private val addFavoriteCityUseCase: AddFavoriteCityUseCase,
    private val removeFavoriteCityUseCase: RemoveFavoriteCityUseCase,
    private val loadFavoriteCitiesUseCase: LoadFavoriteCitiesUseCase
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
                        isCurrentLocation = isHome
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

    fun addCityToFavorites(city: City) {
        viewModelScope.launch {
            addFavoriteCityUseCase(city)
        }
    }

    fun removeCityToFavorites(city: City) {
        viewModelScope.launch {
            removeFavoriteCityUseCase(city)
        }
    }
}