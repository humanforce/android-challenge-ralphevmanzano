package com.humanforce.humanforceandroidengineeringchallenge.core.shared.utils

import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

object DateUtils {

    fun dateToDayOfWeek(strDate: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = sdf.parse(strDate)
        val calendar = Calendar.getInstance()
        calendar.time = date
        if (date == null) return ""
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> "Mon"
            Calendar.TUESDAY -> "Tue"
            Calendar.WEDNESDAY -> "Wed"
            Calendar.THURSDAY -> "Thu"
            Calendar.FRIDAY -> "Fri"
            Calendar.SATURDAY -> "Sat"
            Calendar.SUNDAY -> "Sun"
            else -> ""
        }
    }
}