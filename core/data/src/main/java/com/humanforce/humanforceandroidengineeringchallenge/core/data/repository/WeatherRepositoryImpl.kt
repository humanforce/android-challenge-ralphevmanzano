package com.humanforce.humanforceandroidengineeringchallenge.core.data.repository

import com.humanforce.humanforceandroidengineeringchallenge.core.domain.datasource.local.WeatherLocalDataSource
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.datasource.remote.WeatherRemoteDataSource
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.Forecast
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.WeatherInfo
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    private val weatherLocalDataSource: WeatherLocalDataSource
): WeatherRepository {

    override fun getCurrentWeather(
        lat: Double,
        long: Double,
        onStart: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<WeatherInfo> =
        weatherRemoteDataSource.getCurrentWeather(lat, long, onStart, onError)

    override fun getWeatherForecast(
        lat: Double,
        long: Double,
        onStart: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Forecast>> =
        weatherRemoteDataSource.getWeatherForecast(lat, long, onStart, onError)

    override fun getCities(
        query: String,
        onStart: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<City>> =
        weatherRemoteDataSource.getCities(query, onStart, onError)

    override fun loadFavoriteCities(): Flow<List<City>> = weatherLocalDataSource.loadFavoriteCities()

    override suspend fun addFavoriteCity(city: City) = weatherLocalDataSource.addFavoriteCity(city)

    override suspend fun removeFavoriteCity(id: Int) = weatherLocalDataSource.removeFavoriteCity(id)
}