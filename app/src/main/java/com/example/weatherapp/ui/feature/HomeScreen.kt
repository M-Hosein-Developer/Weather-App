package com.example.weatherapp.ui.feature

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import com.example.weatherapp.model.dataClass.Search12HourForecast
import com.example.weatherapp.ui.theme.DarkBlue
import com.example.weatherapp.ui.theme.LightBlue
import com.example.weatherapp.ui.theme.White
import com.example.weatherapp.ui.theme.gradiantBlue1
import com.example.weatherapp.ui.theme.gradiantBlue2
import com.example.weatherapp.ui.theme.noColor
import com.example.weatherapp.viewModel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {

    homeViewModel.searchByGeoPosition("35.6950453,52.0321925")

    val data5Day = homeViewModel.search5DayForecast()
    val data12Hour = homeViewModel.search12HourForecast()

    Log.v("Dataget", data12Hour.toString())

    val iconPhrase = data5Day[0].Day.IconPhrase
    val temp = data5Day[0].Temperature.Minimum.Value
    val temperature = ((temp - 32) / 1.8).toInt()



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

        WeatherStatus(data5Day, data12Hour, iconPhrase, temperature)

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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherStatus(
    data5Day: List<DailyForecast>,
    data12Hour: List<Search12HourForecast>,
    iconPhrase: String,
    temperature: Int
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp, bottom = 0.dp, start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            modifier = Modifier.size(130.dp),
            contentDescription = null,
            model = when (data5Day[0].Day.IconPhrase) {
                "Cloudy" -> {
                    R.drawable.cloudy
                }

                "Mostly cloudy" -> {
                    R.drawable.sun_cloudy
                }

                "Partly sunny" -> {
                    R.drawable.sun_cloudy
                }

                "Intermittent clouds" -> {
                    R.drawable.mostly_sunny
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
            text = iconPhrase,
            Modifier.padding(top = 16.dp),
            style = TextStyle(
                fontSize = 32.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light
            ),
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
            }
        )

        Text(
            text = "$temperature°",
            Modifier.padding(top = 14.dp, bottom = 14.dp),
            style = TextStyle(
                fontSize = 42.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            ),
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
            }
        )

        WeatherForecastDay(data5Day, iconPhrase)

        WeatherForecastHour(data12Hour, iconPhrase)
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherForecastDay(data: List<DailyForecast>, iconPhrase: String) {

    Column {

        Text(
            text = "5-day forecast",
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp),
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
        ) {

            items(data.size) {
                ForecastDayItem(data[it])
            }

        }

    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastDayItem(data: DailyForecast) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .background(noColor)
            .padding(end = 12.dp, start = 10.dp)
    ) {


        val inputString = data.Date.substring(0, 9)

        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = format.parse(inputString)

        val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(date)


        Text(
            text = dayOfWeek,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light
            ),
            modifier = Modifier.padding(bottom = 8.dp),
            color = when (data.Day.IconPhrase) {
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
            modifier = Modifier
                .size(52.dp)
                .padding(top = 4.dp),
            contentDescription = null,
            model = when (data.Day.IconPhrase) {
                "Cloudy" -> {
                    R.drawable.cloudy
                }

                "Mostly cloudy" -> {
                    R.drawable.sun_cloudy
                }

                "Partly sunny" -> {
                    R.drawable.sun_cloudy
                }

                "Intermittent clouds" -> {
                    R.drawable.mostly_sunny
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

        val temp = data.Temperature.Minimum.Value
        val temperature = ((temp - 32) / 1.8).toInt()

        Text(
            text = "$temperature°",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            ),
            color = when (data.Day.IconPhrase) {
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
            modifier = Modifier.padding(top = 4.dp)
        )

    }

}


@Composable
fun WeatherForecastHour(data: List<Search12HourForecast>, iconPhrase: String) {

    Column {

        Text(
            text = "12-hour forecast",
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp, top = 14.dp),
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
        ) {

            items(data.size) {
                ForecastHourItem(data[it])
            }

        }

    }
}

@Composable
fun ForecastHourItem(data: Search12HourForecast) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .background(noColor)
            .padding(end = 12.dp, start = 10.dp)
    ) {

        Text(
            text = data.DateTime.substring(11, 16),
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light
            ),
            modifier = Modifier.padding(end = 12.dp, start = 10.dp),
            color = when (data.IconPhrase) {
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
            modifier = Modifier
                .size(52.dp)
                .padding(top = 4.dp),
            contentDescription = null,
            model = when (data.IconPhrase) {
                "Cloudy" -> {
                    R.drawable.cloudy
                }

                "Mostly cloudy" -> {
                    R.drawable.sun_cloudy
                }

                "Partly sunny" -> {
                    R.drawable.sun_cloudy
                }

                "Partly cloudy" -> {
                    R.drawable.sun_cloudy
                }

                "Mostly clear" -> {
                    R.drawable.mostly_sunny
                }

                "Intermittent clouds" -> {
                    R.drawable.mostly_sunny
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

        val temp = data.Temperature.Value
        val temperature = ((temp - 32) / 1.8).toInt()

        Text(
            text = "$temperature°",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            ),
            color = when (data.IconPhrase) {
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
            modifier = Modifier.padding(top = 4.dp)
        )

    }

}
