package com.humanforce.humanforceandroidengineeringchallenge.core.domain.usecase

import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadFavoriteCitiesUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    operator fun invoke(): Flow<List<City>> {
        return weatherRepository.loadFavoriteCities()
    }
}