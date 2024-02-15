package com.example.weatherapp.ui.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.ui.theme.DarkBlue
import com.example.weatherapp.ui.theme.LightBlue
import com.example.weatherapp.ui.theme.White
import com.example.weatherapp.ui.theme.gradiantBlue1
import com.example.weatherapp.ui.theme.gradiantBlue2
import com.example.weatherapp.ui.theme.noColor
import com.example.weatherapp.viewModel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DetailScreen(homeViewModel: HomeViewModel, date: String) {

    val data = homeViewModel.search5DayForecast()

    data.forEach {

        if (date == it.EpochDate.toString()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors =
                            when (it.Day.IconPhrase) {
                                "Cloudy", "Rainy", "Mostly cloudy w/ showers" -> {
                                    listOf(DarkBlue, DarkBlue)
                                }

                                "Snow", "Lightning", "Hazy sunshine" -> {
                                    listOf(LightBlue, White)
                                }

                                "Sunny", "Mostly clear", "Intermittent clouds", "Mostly sunny", "Showers" -> {
                                    listOf(gradiantBlue1, gradiantBlue2)
                                }

                                "Mostly cloudy", "Flurries" -> {
                                    listOf(DarkBlue, LightBlue)
                                }

                                else -> {
                                    listOf(DarkBlue, DarkBlue)
                                }
                            }
                        )
                    )
            ) {



                val inputString = it.Date.substring(0, 10)

                val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date = format.parse(inputString)

                val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(date!!)

                DetailScreenToolbar(
                    dayOfWeek,
                    it.Day.IconPhrase,
                )


            }

        }

    }

    //screen
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenToolbar(cityName: String, iconPhrase: String) {

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(noColor),
        title = {
            Text(
                text = cityName,
                Modifier.padding(start = 16.dp, top = 16.dp),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold
                ),
                color =
                when (iconPhrase) {
                    "Cloudy", "Rainy", "Lightning", "Sunny", "Mostly cloudy", "Mostly clear", "Intermittent clouds", "Mostly sunny" ,"Mostly cloudy w/ showers" -> {
                        White
                    }

                    "Snow", "Lightning", "Hazy sunshine" -> {
                        DarkBlue
                    }

                    else -> {
                        White
                    }
                }
            )
        }
    )
}
