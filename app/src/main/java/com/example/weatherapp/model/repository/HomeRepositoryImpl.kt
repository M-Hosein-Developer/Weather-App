package com.example.weatherapp.model.repository

import com.example.weatherapp.model.apiService.ApiService
import com.example.weatherapp.model.dataClass.DailyForecast
import com.example.weatherapp.model.dataClass.Search12HourForecast
import com.example.weatherapp.model.dataClass.SearchLocationResponse
import com.example.weatherapp.util.API_KEY
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val apiService: ApiService) : HomeRepository {


    override suspend fun searchByGeoPosition(location: String): SearchLocationResponse {
        return apiService.searchByGeoPosition(API_KEY, location)
    }

    override suspend fun search5DayForecast(keyCity: String): List<DailyForecast> {
        return apiService.get5DayForecast(keyCity, API_KEY).DailyForecasts
    }

    override suspend fun search12HourForecast(keyCity: String): List<Search12HourForecast> {
        return apiService.get12HourHourlyForecast(keyCity, API_KEY)
    }


}