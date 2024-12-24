package com.humanforce.humanforceandroidengineeringchallenge.core.network.di

import com.humanforce.humanforceandroidengineeringchallenge.core.network.Dispatcher
import com.humanforce.humanforceandroidengineeringchallenge.core.network.WeatherDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @Dispatcher(WeatherDispatchers.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

}