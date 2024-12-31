package com.humanforce.humanforceandroidengineeringchallenge.core.domain.usecase

import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.repository.WeatherRepository
import javax.inject.Inject

class RemoveFavoriteCityUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(city: City) {
        weatherRepository.removeFavoriteCity(city.id)
    }
}