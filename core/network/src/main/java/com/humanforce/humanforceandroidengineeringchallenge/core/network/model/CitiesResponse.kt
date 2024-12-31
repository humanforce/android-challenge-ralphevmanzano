package com.humanforce.humanforceandroidengineeringchallenge.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.City as CityModel

@Serializable
data class CitiesResponse(
    val name: String,
    @SerialName("local_names")
    val localNames: Map<String, String>? = null,
    val lat: Double,
    val lon: Double,
    val country: String,
)

fun CitiesResponse.toCity(): CityModel {
    return CityModel(
        name = name,
        lat = lat,
        long = lon,
        country = country,
    )
}