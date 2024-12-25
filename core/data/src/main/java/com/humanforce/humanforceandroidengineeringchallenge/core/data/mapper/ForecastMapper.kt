package com.humanforce.humanforceandroidengineeringchallenge.core.data.mapper

import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.Forecast
import com.humanforce.humanforceandroidengineeringchallenge.core.network.model.FiveDayForecastResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object ForecastMapper {

    fun toForecastList(forecastResponse: FiveDayForecastResponse): List<Forecast> {
        val timezone = forecastResponse.city.timezone * 1000L
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("GMT")

        // Group forecasts by day
        val forecastsByDay = forecastResponse.list.groupBy {
            // TODO: move to a DateUtil class later
            sdf.format(Date(it.dt * 1000L + timezone))
        }

        return forecastsByDay.map { (day, forecasts) ->
            // For simplicity, just taking the first entry of weather info per day
            val weather = forecasts.first().weather.firstOrNull()

            Forecast(
                dt = forecasts.first().dt,
                dtTxt = day,
                tempMin = forecasts.minOf { it.main.tempMin },
                tempMax = forecasts.maxOf { it.main.tempMax },
                weatherMain = weather?.main.orEmpty(),
                weatherDescription = weather?.description.orEmpty(),
                weatherIcon = weather?.icon.orEmpty()
            )
        }
    }
}