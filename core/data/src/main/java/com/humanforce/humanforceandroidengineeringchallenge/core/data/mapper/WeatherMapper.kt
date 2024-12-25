package com.humanforce.humanforceandroidengineeringchallenge.core.data.mapper

import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.WeatherInfo
import com.humanforce.humanforceandroidengineeringchallenge.core.network.model.CurrentWeatherResponse

object WeatherMapper {

    fun mapToWeatherInfo(weatherResponse: CurrentWeatherResponse): WeatherInfo {
        return WeatherInfo(
            temp = weatherResponse.main.temp.toInt(),
            feelsLike = weatherResponse.main.feelsLike,
            tempMin = weatherResponse.main.tempMin.toInt(),
            tempMax = weatherResponse.main.tempMax.toInt(),
            pressure = weatherResponse.main.pressure,
            humidity = weatherResponse.main.humidity,
            cityId = weatherResponse.id,
            cityName = weatherResponse.name,
            windSpeed = weatherResponse.wind.speed,
            clouds = weatherResponse.clouds.all,
            visibility = weatherResponse.visibility,
            weatherId = weatherResponse.weather.first().id,
            weatherMain = weatherResponse.weather.first().main,
            weatherDescription = weatherResponse.weather.first().description,
            weatherIconUrl = buildIconUrl(weatherResponse.weather.first().icon)
        )
    }

    private fun buildIconUrl(iconCode: String): String {
        return if (iconCode.isNotBlank()) "https://openweathermap.org/img/wn/$iconCode@2x.png"
        else ""
    }
}