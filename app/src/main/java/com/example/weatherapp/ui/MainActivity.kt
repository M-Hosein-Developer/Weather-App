package com.example.weatherapp.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.model.dataClass.DailyForecast
import com.example.weatherapp.ui.feature.HomeScreen
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.util.coroutineExceptionHandler
import com.example.weatherapp.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            WeatherAppTheme(darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    UiWeatherApp(homeViewModel)

                }
            }
        }
    }



}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UiWeatherApp(homeViewModel: HomeViewModel) {

    HomeScreen(homeViewModel)

}

