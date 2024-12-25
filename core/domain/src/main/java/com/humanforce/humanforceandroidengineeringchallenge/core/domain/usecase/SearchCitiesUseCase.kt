package com.humanforce.humanforceandroidengineeringchallenge.core.domain.usecase

import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCitiesUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(
        query: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<City>> {
        return weatherRepository.getCities(query, onStart, onComplete, onError)
    }
}