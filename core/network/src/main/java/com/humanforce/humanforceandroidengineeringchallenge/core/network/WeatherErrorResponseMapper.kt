package com.humanforce.humanforceandroidengineeringchallenge.core.network

import com.humanforce.humanforceandroidengineeringchallenge.core.network.model.WeatherErrorResponse
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.mappers.ApiErrorModelMapper
import com.skydoves.sandwich.message
import com.skydoves.sandwich.retrofit.statusCode

object WeatherErrorResponseMapper : ApiErrorModelMapper<WeatherErrorResponse> {

    /**
     * maps the [ApiResponse.Failure.Error] to the [WeatherErrorResponse] using the mapper.
     *
     * @param apiErrorResponse The [ApiResponse.Failure.Error] error response from the network request.
     * @return A customized [WeatherErrorResponse] error response.
     */
    override fun map(apiErrorResponse: ApiResponse.Failure.Error): WeatherErrorResponse {
        return WeatherErrorResponse(apiErrorResponse.statusCode.code, apiErrorResponse.message())
    }
}