package com.example.weatherapp.ui.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapp.R
import com.example.weatherapp.model.dataClass.DailyForecast
import com.example.weatherapp.ui.theme.DarkBlue
import com.example.weatherapp.ui.theme.LightBlue
import com.example.weatherapp.ui.theme.White
import com.example.weatherapp.ui.theme.gradiantBlue1
import com.example.weatherapp.ui.theme.gradiantBlue2
import com.example.weatherapp.ui.theme.noColor
import com.example.weatherapp.viewModel.HomeViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {

    homeViewModel.searchByGeoPosition("35.6950453,52.0321925")

    val data = homeViewModel.search5day.value

    val iconPhrase = "data[0].Day.IconPhrase"
    val temp = data[0].Temperature.Minimum.Value
    val temperature = ((temp - 32) / 1.8).toInt()
    val time = "12:20"


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors =
                    when (iconPhrase) {
                        "Cloudy", "Rainy", "Lightning" -> {
                            listOf<Color>(DarkBlue, DarkBlue)
                        }

                        "Snowy" -> {
                            listOf<Color>(LightBlue, White)
                        }

                        "Sunny" -> {
                            listOf<Color>(gradiantBlue1, gradiantBlue2)
                        }

                        else -> {
                            listOf<Color>(DarkBlue, DarkBlue)
                        }
                    }
                )
            )
    ) {

        HomeScreenToolbar(homeViewModel.city.value, "data[0].Day.IconPhrase")
        WeatherStatus(time , iconPhrase , temperature)

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenToolbar(cityName: String, iconPhrase: String) {

    TopAppBar(
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
                    "Cloudy", "Rainy", "Lightning" -> { White }

                    "Snowy" -> { DarkBlue }

                    "Sunny" -> { White }

                    else -> {
                        White
                    }
                }
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(noColor)
    )

}

@Composable
fun WeatherStatus(time : String , iconPhrase: String, temperature: Int) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp, bottom = 0.dp, start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            modifier = Modifier.size(160.dp),
            contentDescription = null,
            model = when (iconPhrase) {
                "Cloudy" -> {
                    R.drawable.cloudy
                }

                "Lightning" -> {
                    R.drawable.lightning
                }

                "Snowy" -> {
                    R.drawable.mostly_sunny
                }

                "Clear" -> {
                    R.drawable.suny
                }

                "Mostly sunny" -> {
                    R.drawable.suny
                }

                "Rainy" -> {
                    R.drawable.rainy
                }

                else -> {
                    White
                }
            }
        )

        Text(
            text = iconPhrase,
            Modifier.padding(top = 16.dp),
            style = TextStyle(
                fontSize = 42.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light
            ),
            color =
            when (iconPhrase) {
                "Cloudy", "Rainy", "Lightning" -> {
                    White
                }

                "Snowy" -> {
                    DarkBlue
                }

                "Sunny" -> {
                    White
                }

                else -> {
                    White
                }
            }
        )

        Text(
            text = "$temperature°",
            Modifier.padding(top = 14.dp),
            style = TextStyle(
                fontSize = 48.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            ),
            color =
            when (iconPhrase) {
                "Cloudy", "Rainy", "Lightning" -> { White }

                "Snowy" -> { DarkBlue }

                "Sunny" -> { White }

                else -> { White }
            }
        )

        WeatherForecastDay(time , iconPhrase , temperature)

        WeatherForecastHour(time , iconPhrase , temperature)
    }


}

@Composable
fun WeatherForecastDay(time : String , iconPhrase: String , temperature: Int) {

    Column {

        Text(
            text = "5-day forecast",
            modifier = Modifier.padding(start = 16.dp , bottom = 4.dp),
            color = when (iconPhrase) {
                "Cloudy", "Rainy", "Lightning" -> {
                    White
                }

                "Snowy" -> {
                    DarkBlue
                }

                "Sunny" -> {
                    White
                }

                else -> {
                    White
                }
            },
            style = TextStyle(fontWeight = FontWeight.Bold)
        )

        LazyRow(
            contentPadding = PaddingValues(top = 16.dp , bottom = 16.dp , start = 6.dp , end = 4.dp),
            verticalAlignment = Alignment.Bottom
        ){

            items(5){
                ForecastDayItem(time , iconPhrase , temperature)
            }

        }

    }



}

@Composable
fun ForecastDayItem(time: String, iconPhrase: String, temperature: Int) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .background(noColor)
            .padding(end = 10.dp)
    ) {

        Text(
            text = time ,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light
            ),
            modifier = Modifier.padding(bottom = 8.dp),
            color =
            when (iconPhrase) {
                "Cloudy", "Rainy", "Lightning" -> {
                    White
                }

                "Snowy" -> {
                    DarkBlue
                }

                "Sunny" -> {
                    White
                }

                else -> {
                    White
                }
            }
        )

        AsyncImage(
            modifier = Modifier.size(64.dp),
            contentDescription = null,
            model = when (iconPhrase) {
                "Cloudy" -> {
                    R.drawable.cloudy
                }

                "Lightning" -> {
                    R.drawable.lightning
                }

                "Snowy" -> {
                    R.drawable.snowy
                }

                "Sunny" -> {
                    R.drawable.suny
                }

                "Rainy" -> {
                    R.drawable.rainy
                }

                else -> {
                    White
                }
            }
        )

        Text(
            text = temperature.toString() + "°",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            ),
            color =
            when (iconPhrase) {
                "Cloudy", "Rainy", "Lightning" -> {
                    White
                }

                "Snowy" -> {
                    DarkBlue
                }

                "Sunny" -> {
                    White
                }

                else -> {
                    White
                }
            }
        )

    }

}


@Composable
fun WeatherForecastHour(time : String , iconPhrase: String , temperature: Int) {

    Column {

        Text(
            text = "12-hour forecast",
            modifier = Modifier.padding(start = 16.dp , bottom = 4.dp),
            color = when (iconPhrase) {
                "Cloudy", "Rainy", "Lightning" -> {
                    White
                }

                "Snowy" -> {
                    DarkBlue
                }

                "Sunny" -> {
                    White
                }

                else -> {
                    White
                }
            },
            style = TextStyle(fontWeight = FontWeight.Bold)
        )

        LazyRow(
            contentPadding = PaddingValues(top = 16.dp , bottom = 16.dp , start = 6.dp , end = 4.dp),
            verticalAlignment = Alignment.Bottom
        ){

            items(10){
                ForecastHourItem(time , iconPhrase , temperature)
            }

        }

    }



}

@Composable
fun ForecastHourItem(time: String, iconPhrase: String, temperature: Int) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .background(noColor)
            .padding(end = 10.dp)
    ) {

        Text(
            text = time ,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light
            ),
            modifier = Modifier.padding(bottom = 8.dp),
            color =
            when (iconPhrase) {
                "Cloudy", "Rainy", "Lightning" -> {
                    White
                }

                "Snowy" -> {
                    DarkBlue
                }

                "Sunny" -> {
                    White
                }

                else -> {
                    White
                }
            }
        )

        AsyncImage(
            modifier = Modifier.size(64.dp),
            contentDescription = null,
            model = when (iconPhrase) {
                "Cloudy" -> {
                    R.drawable.cloudy
                }

                "Lightning" -> {
                    R.drawable.lightning
                }

                "Snowy" -> {
                    R.drawable.snowy
                }

                "Sunny" -> {
                    R.drawable.suny
                }

                "Rainy" -> {
                    R.drawable.rainy
                }

                else -> {
                    White
                }
            }
        )

        Text(
            text = temperature.toString() + "°",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            ),
            color =
            when (iconPhrase) {
                "Cloudy", "Rainy", "Lightning" -> {
                    White
                }

                "Snowy" -> {
                    DarkBlue
                }

                "Sunny" -> {
                    White
                }

                else -> {
                    White
                }
            }
        )

    }

}
