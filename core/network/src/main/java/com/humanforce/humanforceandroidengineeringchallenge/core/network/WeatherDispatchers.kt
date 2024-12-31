package com.humanforce.humanforceandroidengineeringchallenge.core.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val weatherDispatchers: WeatherDispatchers)

enum class WeatherDispatchers {
    IO
}