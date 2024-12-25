package com.humanforce.humanforceandroidengineeringchallenge.core.domain.model

data class City(
    val name: String,
    val lat: Double,
    val long: Double,
    val country: String,
    val state: String? = null
)
