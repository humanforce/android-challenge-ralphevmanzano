package com.humanforce.humanforceandroidengineeringchallenge.core.domain.datasource.remote

import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.Forecast
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRemoteDataSource {

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
}