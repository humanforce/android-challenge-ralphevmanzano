package com.humanforce.humanforceandroidengineeringchallenge.core.network.service

import com.humanforce.humanforceandroidengineeringchallenge.core.network.APIKeyManager
import com.humanforce.humanforceandroidengineeringchallenge.core.network.model.CurrentWeatherResponse
import com.humanforce.humanforceandroidengineeringchallenge.core.network.model.FiveDayForecastResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") appId: String = APIKeyManager.apiKey
    ): ApiResponse<CurrentWeatherResponse>

    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") appId: String = APIKeyManager.apiKey
    ): ApiResponse<FiveDayForecastResponse>

}