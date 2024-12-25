package com.humanforce.humanforceandroidengineeringchallenge.core.domain.usecase

import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.WeatherInfo
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.repository.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GetCurrentWeatherAndForecastUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(
        lat: Double,
        long: Double,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<WeatherInfo> {
        // Get currentWeather, then get forecast and append it to WeatherInfo
        return weatherRepository.getCurrentWeather(lat, long, onStart, { }, onError)
            .flatMapConcat { weatherInfo ->
                weatherRepository.getWeatherForecast(lat, long, { }, onComplete, onError)
                    .map { forecastList ->
                        weatherInfo.copy(forecast = forecastList)
                    }
            }
    }
}