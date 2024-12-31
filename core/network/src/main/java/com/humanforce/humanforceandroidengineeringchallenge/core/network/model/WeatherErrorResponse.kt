package com.humanforce.humanforceandroidengineeringchallenge.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherErrorResponse(
    val cod: Int,
    val message: String
)

