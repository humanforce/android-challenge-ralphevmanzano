package com.humanforce.humanforceandroidengineeringchallenge.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FiveDayForecastResponse(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<Forecast>,
    val city: City
)

@Serializable
data class Forecast(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val rain: Rain? = null,
    val sys: SysPod,
    @SerialName("dt_txt") val dtTxt: String
)

@Serializable
data class SysPod(
    val pod: String
)

@Serializable
data class City(
    val id: Int,
    val name: String,
    @SerialName("coord")
    val coordinates: Coordinates,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Long,
    val sunset: Long
)