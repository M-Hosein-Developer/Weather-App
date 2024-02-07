package com.example.weatherapp.model.repository

import android.util.Log
import com.example.weatherapp.model.apiService.ApiService
import com.example.weatherapp.model.dataClass.DailyForecast
import com.example.weatherapp.model.dataClass.SearchLocationResponse
import com.example.weatherapp.util.API_KEY
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val apiService: ApiService) : HomeRepository {

    private var keyCity = ""

    override suspend fun searchByGeoPosition(location: String): SearchLocationResponse {

        val result = apiService.searchByGeoPosition(API_KEY, location)
        keyCity = result.Key

        return result
    }


    override suspend fun search5DayForecast(): List<DailyForecast> {


        val result = apiService.get5DayForecast("211331", API_KEY).DailyForecasts
        Log.v("testApirep", result.toString())

        return result
    }


}