package com.humanforce.humanforceandroidengineeringchallenge.core.data.datasource.local

import com.humanforce.humanforceandroidengineeringchallenge.core.data.mapper.CityMapper
import com.humanforce.humanforceandroidengineeringchallenge.core.database.CityDao
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.datasource.local.WeatherLocalDataSource
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City
import com.humanforce.humanforceandroidengineeringchallenge.core.network.Dispatcher
import com.humanforce.humanforceandroidengineeringchallenge.core.network.WeatherDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherLocalDataSourceImpl @Inject constructor(
    private val cityDao: CityDao,
    @Dispatcher(WeatherDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
): WeatherLocalDataSource {

    override fun loadFavoriteCities(): Flow<List<City>> {
        return cityDao.loadFavoriteCities().map {
            it.map { cityEntity ->
                CityMapper.toDomain(cityEntity)
            }
        }
    }

    override suspend fun addFavoriteCity(city: City) {
        withContext(ioDispatcher) {
            cityDao.addFavoriteCity(CityMapper.toEntity(city))
        }
    }

    override suspend fun removeFavoriteCity(id: Int) {
        withContext(ioDispatcher) {
            cityDao.removeFavoriteCity(id)
        }
    }
}