package com.humanforce.humanforceandroidengineeringchallenge.core.data.di

import com.humanforce.humanforceandroidengineeringchallenge.core.data.datasource.local.WeatherLocalDataSourceImpl
import com.humanforce.humanforceandroidengineeringchallenge.core.data.datasource.remote.WeatherRemoteDataSourceImpl
import com.humanforce.humanforceandroidengineeringchallenge.core.data.repository.WeatherRepositoryImpl
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.datasource.local.WeatherLocalDataSource
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.datasource.remote.WeatherRemoteDataSource
import com.humanforce.humanforceandroidengineeringchallenge.core.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindWeatherRemoteDataSource(weatherRemoteDataSourceImpl: WeatherRemoteDataSourceImpl): WeatherRemoteDataSource

    @Binds
    fun bindWeatherLocalDataSource(weatherLocalDataSourceImpl: WeatherLocalDataSourceImpl): WeatherLocalDataSource

    @Binds
    fun bindWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository
}