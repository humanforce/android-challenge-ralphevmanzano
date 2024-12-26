package com.humanforce.humanforceandroidengineeringchallenge.core.domain.model

data class WeatherInfo(
    val temp: Int = 0,
    val feelsLike: Int = 0,
    val tempMin: Int = 0,
    val tempMax: Int = 0,
    val pressure: Int = 0,
    val humidity: Int = 0,
    val cityId: Int = 0,
    val cityName: String = "",
    val country: String = "",
    val long: Double = 0.0,
    val lat: Double = 0.0,
    val windSpeed: Double = 0.0,
    val clouds: Int = 0,
    val visibility: Int = 0,
    // these fields are extracted from the primary weather
    // first one on List<Weather>
    val weatherId: Int = 0,
    val weatherMain: String = "",
    val weatherDescription: String = "",
    val weatherIconUrl: String = "",
    val forecast: List<Forecast> = emptyList()
)

data class Forecast(
    val dt: Long = 0,
    val day: String = "",
    val tempMin: Double = 0.0,
    val tempMax: Double = 0.0,
    val weatherMain: String = "",
    val weatherDescription: String = "",
    val weatherIconUrl: String = "",
)
