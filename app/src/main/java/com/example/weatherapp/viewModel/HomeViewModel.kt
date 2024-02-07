package com.example.weatherapp.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.dataClass.DailyForecast
import com.example.weatherapp.model.repository.HomeRepository
import com.example.weatherapp.util.coroutineExceptionHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    val city = mutableStateOf("")
    val search5day = mutableStateOf(listOf<DailyForecast>())

    init {
        search5DayForecast()
    }

    fun searchByGeoPosition(location: String) {

        viewModelScope.launch(coroutineExceptionHandler) {

            val result = repository.searchByGeoPosition(location)

            Log.v("resultResponse", result.EnglishName)
            city.value = result.EnglishName

        }
    }

    private fun search5DayForecast() {

        viewModelScope.launch(coroutineExceptionHandler) {
            search5day.value = repository.search5DayForecast()
            Log.v("testAPiNew" , search5day.value.toString())
        }

    }

}