package com.humanforce.humanforceandroidengineeringchallenge.core.data.mapper

import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.WeatherInfo
import com.humanforce.humanforceandroidengineeringchallenge.core.network.model.CurrentWeatherResponse
import com.humanforce.humanforceandroidengineeringchallenge.core.shared.extensions.buildIconUrl

object WeatherMapper {

    fun toWeatherInfo(weatherResponse: CurrentWeatherResponse): WeatherInfo {
        return WeatherInfo(
            temp = weatherResponse.main.temp.toInt(),
            feelsLike = weatherResponse.main.feelsLike.toInt(),
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
            weatherIconUrl = weatherResponse.weather.first().icon.buildIconUrl(),
            country = weatherResponse.sys.country,
            lat = weatherResponse.coordinates.lat,
            long = weatherResponse.coordinates.lon
        )
    }
}