package com.humanforce.humanforceandroidengineeringchallenge.core.domain.repository

import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.Forecast
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getCurrentWeather(
        lat: Double,
        long: Double,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<WeatherInfo>

    fun getWeatherForecast(
        lat: Double,
        long: Double,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Forecast>>

    fun getCities(
        query: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<City>>

    fun loadFavoriteCities(): Flow<List<City>>
    suspend fun addFavoriteCity(city: City)
    suspend fun removeFavoriteCity(id: Int)
}