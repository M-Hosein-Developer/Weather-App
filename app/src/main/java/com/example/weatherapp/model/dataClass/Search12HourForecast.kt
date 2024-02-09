package com.example.weatherapp.model.dataClass


data class Search12HourForecast(
    val DateTime: String,
    val EpochDateTime: Int,
    val HasPrecipitation: Boolean,
    val IconPhrase: String,
    val IsDaylight: Boolean,
    val Link: String,
    val MobileLink: String,
    val PrecipitationProbability: Int,
    val Temperature: Temperature1,
    val WeatherIcon: Int
) {
    data class Temperature1(
        val Unit: String,
        val UnitType: Int,
        val Value: Double
    )
}
