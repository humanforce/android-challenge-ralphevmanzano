package com.humanforce.humanforceandroidengineeringchallenge.core.network.di

import com.humanforce.humanforceandroidengineeringchallenge.core.network.BuildConfig
import com.humanforce.humanforceandroidengineeringchallenge.core.network.WeatherUnitsInterceptor
import com.humanforce.humanforceandroidengineeringchallenge.core.network.service.GeocodingService
import com.humanforce.humanforceandroidengineeringchallenge.core.network.service.WeatherService
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WeatherRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GeocodingRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WeatherOkHttp

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GeocodingOkHttp

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideJson(): Json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    @WeatherOkHttp
    @Singleton
    @Provides
    fun provideWeatherOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .apply {
            if (BuildConfig.DEBUG) {
                this.addNetworkInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
            }
            this.addNetworkInterceptor(WeatherUnitsInterceptor())
        }.build()

    @GeocodingOkHttp
    @Singleton
    @Provides
    fun provideGeocodingOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .apply {
            if (BuildConfig.DEBUG) {
                this.addNetworkInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
            }
        }.build()

    @WeatherRetrofit
    @Provides
    @Singleton
    fun provideWeatherRetrofit(json: Json, @WeatherOkHttp okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
    }

    @GeocodingRetrofit
    @Provides
    @Singleton
    fun provideGeocodingRetrofit(json: Json, @GeocodingOkHttp okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.openweathermap.org/geo/1.0/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherService(@WeatherRetrofit retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }

    @Provides
    @Singleton
    fun provideGeocodingService(@GeocodingRetrofit retrofit: Retrofit): GeocodingService {
        return retrofit.create(GeocodingService::class.java)
    }
}