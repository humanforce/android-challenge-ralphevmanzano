package com.humanforce.humanforceandroidengineeringchallenge.core.domain.model

data class City(
    val id: Int = 0,
    val name: String,
    val lat: Double,
    val long: Double,
    val country: String,
)
