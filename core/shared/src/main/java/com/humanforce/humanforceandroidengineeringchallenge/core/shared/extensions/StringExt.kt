package com.humanforce.humanforceandroidengineeringchallenge.core.shared.extensions

fun String.buildIconUrl(): String {
    return if (this.isNotBlank()) "https://openweathermap.org/img/wn/$this@2x.png"
    else ""
}