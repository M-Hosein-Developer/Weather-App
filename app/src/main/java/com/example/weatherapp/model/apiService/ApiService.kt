package com.example.weatherapp.model.apiService

import com.example.weatherapp.model.dataClass.Search12HourForecast
import com.example.weatherapp.model.dataClass.Search5DayForecast
import com.example.weatherapp.model.dataClass.SearchLocationResponse
import com.example.weatherapp.model.dataClass.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //Search by location
    @GET("locations/v1/cities/geoposition/search")
    suspend fun searchByGeoPosition(@Query("apikey") apiKey: String, @Query("q") query: String) : SearchLocationResponse

    //Search 5 day forecast
    @GET("forecasts/v1/daily/5day/{locationKey}")
    suspend fun get5DayForecast(@Path("locationKey") locationKey: String, @Query("apikey") apiKey: String) : Search5DayForecast

    //Search 12 hour forecast
    @GET("forecasts/v1/hourly/12hour/{locationKey}")
    suspend fun get12HourHourlyForecast(@Path("locationKey") locationKey: String, @Query("apikey") apiKey: String) : List<Search12HourForecast>

    //Search by name
    @GET("locations/v1/cities/search")
    suspend fun searchCities( @Query("q") query: String, @Query("apikey") apiKey: String) : SearchResponse
}