package com.example.weatherapp.model.repository

import com.example.weatherapp.model.dataClass.DailyForecast
import com.example.weatherapp.model.dataClass.Search12HourForecast
import com.example.weatherapp.model.dataClass.SearchLocationResponse
import com.example.weatherapp.model.dataClass.SearchResponse

interface HomeRepository {

    suspend fun searchByGeoPosition(location : String) : SearchLocationResponse

    suspend fun search5DayForecast(keyCity : String) : List<DailyForecast>

    suspend fun search12HourForecast(keyCity : String) : List<Search12HourForecast>

    suspend fun searchByName(name : String) : SearchResponse
}