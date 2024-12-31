package com.humanforce.humanforceandroidengineeringchallenge.core.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor class for providing units parameter to OpenWeatherAPI
 */
class WeatherUnitsInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val units = "metric"
        val url = original.url.newBuilder().addQueryParameter("units", units).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}