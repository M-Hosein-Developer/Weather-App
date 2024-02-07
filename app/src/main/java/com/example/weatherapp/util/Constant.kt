package com.example.weatherapp.util

import com.example.weatherapp.model.dataClass.DailyForecast
import com.example.weatherapp.model.dataClass.Day1
import com.example.weatherapp.model.dataClass.Maximum1
import com.example.weatherapp.model.dataClass.Minimum1
import com.example.weatherapp.model.dataClass.Night1
import com.example.weatherapp.model.dataClass.Temperature1

const val BASE_URL = "http://dataservice.accuweather.com/"
const val API_KEY = "WFZewOIEbegsXKKQgbm8vofKqHNCNpju"

val EMPTY_DATA = DailyForecast(
    "",
    Day1(false, -1, ""),
    -1,
    "",
    "",
    Night1(false, -1, ""),
    listOf(""),
    Temperature1(Maximum1("" , -1 , 0.0) , Minimum1("" , -1 , 0.0))
)