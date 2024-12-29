package com.humanforce.humanforceandroidengineeringchallenge.feature.cities

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.usecase.AddFavoriteCityUseCase
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.usecase.GetCurrentWeatherAndForecastUseCase
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.usecase.LoadFavoriteCitiesUseCase
import com.humanforce.humanforceandroidengineeringchallenge.core.shared.viewmodel.WeatherUiState
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
class CityPreviewViewModel @Inject constructor(
    private val getCurrentWeatherAndForecastUseCase: GetCurrentWeatherAndForecastUseCase,
    private val addFavoriteCityUseCase: AddFavoriteCityUseCase,
    private val loadFavoriteCitiesUseCase: LoadFavoriteCitiesUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    init {
        val coordinates = savedStateHandle.get<String>("coordinates")
        val lat = coordinates?.split(",")?.first()?.toDoubleOrNull()
        val long = coordinates?.split(",")?.last()?.toDoubleOrNull()
        if (lat != null && long != null) {
            getCurrentWeatherAndForecast(lat, long)
        }
    }

    private fun getCurrentWeatherAndForecast(lat: Double, long: Double) {
        viewModelScope.launch {
            getCurrentWeatherAndForecastUseCase(
                lat,
                long,
                onStart = { _uiState.update { it.copy(isLoading = true) } },
                onError = { error -> _uiState.update { it.copy(isLoading = false, error = error) } }
            ).collect { weatherInfo ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        weatherInfo = weatherInfo,
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
}