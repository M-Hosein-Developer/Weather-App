package com.example.weatherapp.model.dataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

data class Search5DayForecast(
    val DailyForecasts: List<DailyForecast>,
    val Headline: Headline1
)

data class DailyForecast(
    val Date: String,
    val Day: Day1,
    val EpochDate: Int,
    val Link: String,
    val MobileLink: String,
    val Night: Night1,
    val Sources: List<String>,
    val Temperature: Temperature1
)

data class Day1(
    val HasPrecipitation: Boolean,
    val Icon: Int,
    val IconPhrase: String
)

data class Night1(
    val HasPrecipitation: Boolean,
    val Icon: Int,
    val IconPhrase: String
)


data class Temperature1(
    val Maximum: Maximum1,
    val Minimum: Minimum1
)


data class Maximum1(
    val Unit: String,
    val UnitType: Int,
    val Value: Double
)


data class Minimum1(
    val Unit: String,
    val UnitType: Int,
    val Value: Double
)

data class Headline1(
    val Category: String,
    val EffectiveDate: String,
    val EffectiveEpochDate: Int,
    val EndDate: Any,
    val EndEpochDate: Any,
    val Link: String,
    val MobileLink: String,
    val Severity: Int,
    val Text: String
)
