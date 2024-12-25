package com.humanforce.humanforceandroidengineeringchallenge.core.network.model

import kotlinx.serialization.Serializable
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City as CityModel

@Serializable
data class CitiesResponse(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String? = null
)

fun CitiesResponse.toCity(): CityModel {
    return CityModel(
        name = name,
        lat = lat,
        long = lon,
        country = country,
        state = state
    )
}