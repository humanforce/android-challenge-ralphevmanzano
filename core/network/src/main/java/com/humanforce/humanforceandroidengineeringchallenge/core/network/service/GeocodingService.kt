package com.humanforce.humanforceandroidengineeringchallenge.core.network.service

import com.humanforce.humanforceandroidengineeringchallenge.core.network.APIKeyManager
import com.humanforce.humanforceandroidengineeringchallenge.core.network.model.LocationResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingService {

    @GET("geo/1.0/direct")
    suspend fun getLocationsByName(
        @Query("q") name: String,
        @Query("appid") appId: String = APIKeyManager.apiKey
    ): ApiResponse<List<LocationResponse>>
}