package com.humanforce.humanforceandroidengineeringchallenge.core.network.service

import com.humanforce.humanforceandroidengineeringchallenge.core.network.APIKeyManager
import com.humanforce.humanforceandroidengineeringchallenge.core.network.model.CitiesResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingService {

    @GET("direct")
    suspend fun getCities(
        @Query("q") name: String,
        @Query("appid") appId: String = APIKeyManager.apiKey,
        @Query("limit") limit: Int = 5
    ): ApiResponse<List<CitiesResponse>>
}