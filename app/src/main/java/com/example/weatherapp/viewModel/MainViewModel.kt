package com.example.weatherapp.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.dataClass.DailyForecast
import com.example.weatherapp.model.dataClass.Search12HourForecast
import com.example.weatherapp.model.repository.HomeRepository
import com.example.weatherapp.util.EMPTY_DATA_12HOUR
import com.example.weatherapp.util.EMPTY_DATA_5Day
import com.example.weatherapp.util.coroutineExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    //home screen state
    val city = mutableStateOf("")
    val isGettingLocation = mutableStateOf(false)
    private val search5day = mutableStateOf(listOf<DailyForecast>())
    private val search12hour = mutableStateOf(listOf<Search12HourForecast>())
    private val keyCity = mutableStateOf("")
    private val locationCity = mutableStateOf("35.710228,51.337778")

    //Search
    val search = mutableStateOf("")
    val cityName = mutableStateOf("")


    //get forecast with location
    fun searchByGeoPosition(location: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            locationCity.value = location
            val result = repository.searchByGeoPosition(locationCity.value)
            city.value = result.EnglishName
            keyCity.value = result.Key
        }
    }

    //5 day forecast
    fun search5DayForecast(): List<DailyForecast> {
        viewModelScope.launch(coroutineExceptionHandler) {
            search5day.value = repository.search5DayForecast(keyCity.value)
        }

        return search5day.value.ifEmpty {
            EMPTY_DATA_5Day
        }
    }

    //12 hour forecast
    fun search12HourForecast(): List<Search12HourForecast> {
        viewModelScope.launch(coroutineExceptionHandler) {
            search12hour.value = repository.search12HourForecast(keyCity.value)
        }

        return search12hour.value.ifEmpty {
            EMPTY_DATA_12HOUR
        }
    }

    //dot - ui get location
    fun getLocation() {
        viewModelScope.launch(coroutineExceptionHandler) {
            isGettingLocation.value = true
            delay(3000)
            isGettingLocation.value = false
        }
    }

    //Search by name
    fun getDataFromSearch() {

        viewModelScope.launch(coroutineExceptionHandler) {
            Log.v("searchResult", search.value)
            val result = repository.searchByName(search.value)
            cityName.value = result[0].EnglishName
            Log.v("searchResult", result[0].Key)
            keyCity.value = result[0].Key
        }

    }

}