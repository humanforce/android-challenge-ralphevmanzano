package com.humanforce.humanforceandroidengineeringchallenge.core.data.datasource.remote

import com.humanforce.humanforceandroidengineeringchallenge.core.data.mapper.ForecastMapper
import com.humanforce.humanforceandroidengineeringchallenge.core.data.mapper.WeatherMapper
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.datasource.remote.WeatherRemoteDataSource
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.Forecast
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.WeatherInfo
import com.humanforce.humanforceandroidengineeringchallenge.core.network.Dispatcher
import com.humanforce.humanforceandroidengineeringchallenge.core.network.WeatherDispatchers
import com.humanforce.humanforceandroidengineeringchallenge.core.network.WeatherErrorResponseMapper
import com.humanforce.humanforceandroidengineeringchallenge.core.network.model.toCity
import com.humanforce.humanforceandroidengineeringchallenge.core.network.service.GeocodingService
import com.humanforce.humanforceandroidengineeringchallenge.core.network.service.WeatherService
import com.skydoves.sandwich.map
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class WeatherRemoteDataSourceImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val geocodingService: GeocodingService,
    @Dispatcher(WeatherDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : WeatherRemoteDataSource {

    override fun getCurrentWeather(
        lat: Double,
        long: Double,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<WeatherInfo> = flow {
        val response = weatherService.getCurrentWeather(lat, long)
        response.suspendOnSuccess {
            emit(WeatherMapper.toWeatherInfo(data))
        }.onError {
            // Map the ApiResponse.Failure.Error to WeatherErrorResponse
            map(WeatherErrorResponseMapper) { onError(this.message) }
        }.onException {
            onError("An unexpected error occurred. Please try again.")
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)

    override fun getWeatherForecast(
        lat: Double,
        long: Double,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Forecast>> = flow {
        val response = weatherService.getWeatherForecast(lat, long)
        response.suspendOnSuccess {
            emit(ForecastMapper.toForecastList(data))
        }.onError {
            // Map the ApiResponse.Failure.Error to WeatherErrorResponse
            map(WeatherErrorResponseMapper) { onError(this.message) }
        }.onException {
            onError("An unexpected error occurred. Please try again.")
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)

    override fun getCities(
        query: String,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<City>> = flow {
        val response = geocodingService.getCities(query)
        response.suspendOnSuccess {
            emit(data.map { it.toCity() })
        }.onError {
            // Map the ApiResponse.Failure.Error to WeatherErrorResponse
            map(WeatherErrorResponseMapper) { onError(this.message) }
        }.onException {
            onError("An unexpected error occurred. Please try again.")
        }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)
}