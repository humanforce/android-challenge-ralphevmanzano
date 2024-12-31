package com.humanforce.humanforceandroidengineeringchallenge.core.data.mapper

import com.humanforce.humanforceandroidengineeringchallenge.core.domain.model.Forecast
import com.humanforce.humanforceandroidengineeringchallenge.core.network.model.FiveDayForecastResponse
import com.humanforce.humanforceandroidengineeringchallenge.core.shared.extensions.buildIconUrl
import com.humanforce.humanforceandroidengineeringchallenge.core.shared.utils.DateUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.roundToInt

object ForecastMapper {

    fun toForecastList(forecastResponse: FiveDayForecastResponse): List<Forecast> {
        val timezone = forecastResponse.city.timezone * 1000L

        // Group forecasts by day
        val forecastsByDay = forecastResponse.list.groupBy {
            DateUtils.dtToYYYYMMDD(it.dt, timezone)
        }

        return forecastsByDay.map { (day, forecasts) ->
            // For simplicity, just taking the first entry of weather info per day
            val weather = forecasts.first().weather.firstOrNull()

            val minTemp = forecasts.minOf { it.main.tempMin }
            val maxTemp = forecasts.maxOf { it.main.tempMax }

            Forecast(
                dt = forecasts.first().dt,
                day = DateUtils.dateToDayOfWeek(day),
                tempMin = minTemp.roundToInt(),
                tempMax = maxTemp.roundToInt(),
                weatherMain = weather?.main.orEmpty(),
                weatherDescription = weather?.description.orEmpty(),
                weatherIconUrl = weather?.icon.orEmpty().buildIconUrl()
            )
        }.takeLast(5)
    }
}