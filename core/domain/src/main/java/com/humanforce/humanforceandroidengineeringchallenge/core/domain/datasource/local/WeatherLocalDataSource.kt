package com.humanforce.humanforceandroidengineeringchallenge.core.domain.datasource.local

import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDataSource {
    fun loadFavoriteCities(): Flow<List<City>>
    suspend fun addFavoriteCity(city: City)
    suspend fun removeFavoriteCity(id: Int)
}