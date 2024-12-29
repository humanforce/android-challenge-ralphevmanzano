package com.humanforce.humanforceandroidengineeringchallenge.core.network

import com.humanforce.humanforceandroidengineeringchallenge.core.network.model.WeatherErrorResponse
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.mappers.ApiErrorModelMapper
import com.skydoves.sandwich.retrofit.errorBody
import com.skydoves.sandwich.retrofit.statusCode
import kotlinx.serialization.json.Json

object WeatherErrorResponseMapper : ApiErrorModelMapper<WeatherErrorResponse> {

    /**
     * maps the [ApiResponse.Failure.Error] to the [WeatherErrorResponse] using the mapper.
     *
     * @param apiErrorResponse The [ApiResponse.Failure.Error] error response from the network request.
     * @return A customized [WeatherErrorResponse] error response.
     */
    override fun map(apiErrorResponse: ApiResponse.Failure.Error): WeatherErrorResponse {
        val errorBody = apiErrorResponse.errorBody
        return if (errorBody != null) {
            try {
                Json.decodeFromString(
                    WeatherErrorResponse.serializer(),
                    errorBody.string()
                )
            } catch (e: Exception) {
                WeatherErrorResponse(
                    cod = apiErrorResponse.statusCode.code,
                    message = "An unexpected error occurred. Please try again."
                )
            }
        } else {
            WeatherErrorResponse(
                cod = apiErrorResponse.statusCode.code,
                message = "An unexpected error occurred. Please try again."
            )
        }
    }
}