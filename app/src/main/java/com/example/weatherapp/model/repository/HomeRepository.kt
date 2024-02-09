package com.example.weatherapp.model.repository

import com.example.weatherapp.model.dataClass.DailyForecast
import com.example.weatherapp.model.dataClass.Search12HourForecast
import com.example.weatherapp.model.dataClass.Search5DayForecast
import com.example.weatherapp.model.dataClass.SearchLocationResponse

interface HomeRepository {

    suspend fun searchByGeoPosition(location : String) : SearchLocationResponse

    suspend fun search5DayForecast() : List<DailyForecast>

    suspend fun search12HourForecast() : List<Search12HourForecast>

}