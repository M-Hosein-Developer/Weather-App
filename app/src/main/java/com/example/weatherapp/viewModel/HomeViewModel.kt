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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    val city = mutableStateOf("")
    val search5day = mutableStateOf(listOf<DailyForecast>())
    val search12hour = mutableStateOf(listOf<Search12HourForecast>())



    fun searchByGeoPosition(location: String) {

        viewModelScope.launch(coroutineExceptionHandler) {

            val result = repository.searchByGeoPosition(location)

            Log.v("resultResponse", result.EnglishName)
            city.value = result.EnglishName

        }
    }

    fun search5DayForecast() : List<DailyForecast> {

        viewModelScope.launch(coroutineExceptionHandler) {
            search5day.value = repository.search5DayForecast()
            Log.v("testAPiNew" , search5day.value.toString())
        }

        return if (search5day.value.isNotEmpty()){
            search5day.value
        }else{
            EMPTY_DATA_5Day
        }

    }

    fun search12HourForecast() : List<Search12HourForecast> {

        viewModelScope.launch(coroutineExceptionHandler) {
            search12hour.value = repository.search12HourForecast()
        }

        return if (search12hour.value.isNotEmpty()){
            search12hour.value
        }else{
            EMPTY_DATA_12HOUR
        }
    }



}