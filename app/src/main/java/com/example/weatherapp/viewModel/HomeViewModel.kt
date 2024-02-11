package com.example.weatherapp.viewModel

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
    private val search5day = mutableStateOf(listOf<DailyForecast>())
    private val search12hour = mutableStateOf(listOf<Search12HourForecast>())
    private val keyCity = mutableStateOf("")



    fun searchByGeoPosition(location: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            val result = repository.searchByGeoPosition(location)
            city.value = result.EnglishName
            keyCity.value = result.Key
        }
    }

    fun search5DayForecast() : List<DailyForecast> {
        viewModelScope.launch(coroutineExceptionHandler) {
            search5day.value = repository.search5DayForecast(keyCity.value)
        }

        return search5day.value.ifEmpty {
            EMPTY_DATA_5Day
        }
    }

    fun search12HourForecast() : List<Search12HourForecast> {
        viewModelScope.launch(coroutineExceptionHandler) {
            search12hour.value = repository.search12HourForecast(keyCity.value)
        }

        return search12hour.value.ifEmpty {
            EMPTY_DATA_12HOUR
        }
    }



}