package com.example.weatherapp.ui.feature

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.weatherapp.viewModel.HomeViewModel

@Composable
fun DetailScreen(homeViewModel: HomeViewModel, date: String) {
    
    val data = homeViewModel.search5DayForecast()

    data.forEach { 
        
        if (date == it.EpochDate.toString()){
            
            Text(text = it.Day.Icon.toString())
            
        }
        
    }

}