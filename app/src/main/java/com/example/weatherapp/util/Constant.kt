package com.example.weatherapp.util

import com.example.weatherapp.model.dataClass.DailyForecast
import com.example.weatherapp.model.dataClass.Day1
import com.example.weatherapp.model.dataClass.Maximum1
import com.example.weatherapp.model.dataClass.Minimum1
import com.example.weatherapp.model.dataClass.Night1
import com.example.weatherapp.model.dataClass.Search12HourForecast
import com.example.weatherapp.model.dataClass.Temperature1

const val BASE_URL = "http://dataservice.accuweather.com/"
const val API_KEY = "sTJAm3Rc2CQ0C4PO9c7el87oDCu0bl33"

val EMPTY_DATA_5Day = listOf(

    DailyForecast(
        "2024-02-19T07:00:00+03:30",
        Day1(false, 1, "Mostly cloudy"),
        121214333,
        "",
        Night1(false, -1, ""),
        listOf(""),
        Temperature1(Maximum1("" , -1 , 0.0) , Minimum1("" , -1 , 0.0))
    )
)

val EMPTY_DATA_12HOUR = listOf(

    Search12HourForecast(
        "2024-02-09T07:00:00+03:30",
        -1,
        false,
        "",
        false,
        "",
        "",
        -1,
        Search12HourForecast.Temperature1("" , -1 , 0.0),
        1
        )

)
