package com.example.weatherapp.ui.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapp.R
import com.example.weatherapp.model.dataClass.DailyForecast
import com.example.weatherapp.ui.theme.DarkBlue
import com.example.weatherapp.ui.theme.LightBlue
import com.example.weatherapp.ui.theme.White
import com.example.weatherapp.ui.theme.backgroundItem
import com.example.weatherapp.ui.theme.gradiantBlue1
import com.example.weatherapp.ui.theme.gradiantBlue2
import com.example.weatherapp.ui.theme.noColor
import com.example.weatherapp.viewModel.HomeViewModel
import kotlinx.coroutines.launch
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
                    .verticalScroll(rememberScrollState())
                    .background(
                        brush = Brush.verticalGradient(
                            colors =
                            when (it.Day.IconPhrase) {
                                "Snow", "Lightning", "Hazy sunshine" -> {
                                    listOf(LightBlue, White)
                                }

                                "Sunny", "Mostly clear", "Intermittent clouds", "Mostly sunny", "Showers", "Partly sunny" -> {
                                    listOf(gradiantBlue1, gradiantBlue2)
                                }

                                "Mostly cloudy", "Flurries", "Cloudy", "Rain", "Mostly cloudy w/ showers" -> {
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
                val date1 = format.parse(inputString)
                val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(date1!!)

                DetailScreenToolbar(
                    dayOfWeek,
                    it.Day.IconPhrase,
                )

                DailyStatus(it, data)

            }


        }

    }
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
                    "Cloudy", "Rain", "Lightning", "Sunny", "Mostly cloudy", "Mostly clear", "Intermittent clouds", "Mostly sunny", "Mostly cloudy w/ showers" -> {
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

@Composable
fun DailyStatus(onceData: DailyForecast, dataList: List<DailyForecast>) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            modifier = Modifier.size(130.dp),
            contentDescription = null,
            model = when (onceData.Day.IconPhrase) {
                "Cloudy" -> {
                    R.drawable.cloudy
                }

                "Mostly cloudy w/ showers" -> {
                    R.drawable.sun_cloudy
                }

                "Clear" -> {
                    R.drawable.moon
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
                    R.drawable.partly_clear
                }

                "Intermittent clouds" -> {
                    R.drawable.mostly_sunny
                }

                "Mostly sunny" -> {
                    R.drawable.mostly_sunny
                }

                "Lightning" -> {
                    R.drawable.lightning
                }

                "Snow" -> {
                    R.drawable.snowy
                }

                "Sunny" -> {
                    R.drawable.suny
                }

                "Rain" -> {
                    R.drawable.rainy
                }

                "Hazy sunshine" -> {
                    R.drawable.fog
                }

                "Hazy moonlight" -> {
                    R.drawable.partly_clear
                }

                else -> {
                    White
                }
            }
        )

        Text(
            text = onceData.Day.IconPhrase,
            Modifier.padding(top = 16.dp),
            style = TextStyle(
                fontSize = 32.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            ),
            color = when (onceData.Day.IconPhrase) {
                "Cloudy", "Rain", "Lightning", "Sunny", "Mostly cloudy", "Mostly clear", "Intermittent clouds", "Mostly sunny", "Mostly cloudy w/ showers" -> {
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


        Row(verticalAlignment = Alignment.CenterVertically) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                val tempMin = onceData.Temperature.Minimum.Value
                val temperatureMin = ((tempMin - 32) / 1.8).toInt()

                Text(
                    text = "$temperatureMin°",
                    Modifier.padding(top = 14.dp, bottom = 14.dp),
                    style = TextStyle(
                        fontSize = 42.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold
                    ),
                    color = when (onceData.Day.IconPhrase) {
                        "Cloudy", "Rain", "Lightning" -> {
                            White
                        }

                        "Snow" -> {
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
                    text = "Min ",
                    Modifier.padding(bottom = 14.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold
                    ),
                    color = when (onceData.Day.IconPhrase) {
                        "Cloudy", "Rain", "Lightning" -> {
                            White
                        }

                        "Snow" -> {
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

            Spacer(modifier = Modifier.width(64.dp))


            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                val tempMax = onceData.Temperature.Maximum.Value
                val temperatureMax = ((tempMax - 32) / 1.8).toInt()

                Text(
                    text = "$temperatureMax°",
                    Modifier.padding(top = 14.dp, bottom = 14.dp),
                    style = TextStyle(
                        fontSize = 42.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold
                    ),
                    color = when (onceData.Day.IconPhrase) {
                        "Cloudy", "Rain", "Lightning" -> {
                            White
                        }

                        "Snow" -> {
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
                    text = "Max ",
                    Modifier.padding(bottom = 14.dp),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold
                    ),
                    color = when (onceData.Day.IconPhrase) {
                        "Cloudy", "Rain", "Lightning" -> {
                            White
                        }

                        "Snow" -> {
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


        Spacer(modifier = Modifier.height(18.dp))

        DailyForecast(dataList, onceData.Day.IconPhrase, onceData)

    }


}


@Composable
fun DailyForecast(data: List<DailyForecast>, iconPhrase: String, onceData: DailyForecast) {

    val stateRowX = rememberLazyListState() // State for the first Row, X
    val stateRowY = rememberLazyListState() // State for the second Row, Y

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollableState { delta ->
        scope.launch {
            stateRowX.scrollBy(-delta)
            stateRowY.scrollBy(-delta)
        }
        delta
    }

    Column {

        Text(
            text = "Day",
            modifier = Modifier.padding(start = 18.dp, bottom = 4.dp),
            color = when (iconPhrase) {
                "Cloudy", "Rainy", "Lightning", "Sunny", "Mostly cloudy", "Mostly clear", "Intermittent clouds", "Mostly sunny", "Hazy sunshine" -> {
                    White
                }

                "Snow" -> {
                    DarkBlue
                }

                else -> {
                    White
                }
            },
            style = TextStyle(fontWeight = FontWeight.Bold)
        )

        LazyRow(
            state = stateRowY,
            contentPadding = PaddingValues(top = 16.dp,  start = 6.dp, end = 4.dp),
            verticalAlignment = Alignment.Bottom
        ) {

            items(data.size) {
                DailyForecastDayItem(data[it], onceData)
            }

        }


//        Spacer(modifier = Modifier.height(20.dp))

        LazyRow(
            state = stateRowX,
            contentPadding = PaddingValues( bottom = 16.dp, start = 6.dp, end = 4.dp),
            verticalAlignment = Alignment.Bottom
        ) {

            items(data.size) {
                DailyForecastNightItem(data[it], onceData)
            }

        }



        Text(
            text = "Night",
            modifier = Modifier.padding(start = 18.dp, top = 4.dp),
            color = when (iconPhrase) {
                "Cloudy", "Rainy", "Lightning", "Sunny", "Mostly cloudy", "Mostly clear", "Intermittent clouds", "Mostly sunny", "Hazy sunshine" -> {
                    White
                }

                "Snow" -> {
                    DarkBlue
                }

                else -> {
                    White
                }
            },
            style = TextStyle(fontWeight = FontWeight.Bold)
        )
    }


    LaunchedEffect(stateRowX.firstVisibleItemScrollOffset) {
        stateRowY.scrollToItem(
            stateRowX.firstVisibleItemIndex,
            stateRowX.firstVisibleItemScrollOffset
        )
    }

    LaunchedEffect(stateRowY.firstVisibleItemScrollOffset) {
        stateRowX.scrollToItem(
            stateRowY.firstVisibleItemIndex,
            stateRowY.firstVisibleItemScrollOffset
        )
    }

}

@Composable
fun DailyForecastDayItem(data: DailyForecast, onceData: DailyForecast) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .background(noColor)
            .padding(end = 12.dp, start = 8.dp)
            .background(
                color =
                if (data.Date == onceData.Date) {
                    backgroundItem
                } else {
                    noColor
                },
                shape = RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp)
            )
    ) {


        //convert date to day
        val inputString = data.Date.substring(0, 10)
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = format.parse(inputString)
        val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(date!!)


        Text(
            text = dayOfWeek,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light
            ),
            modifier = Modifier.padding(8.dp),
            color = when (data.Night.IconPhrase) {
                "Cloudy", "Rainy", "Lightning", "Sunny", "Mostly cloudy", "Mostly clear", "Intermittent clouds", "Mostly sunny", "Hazy sunshine" -> {
                    White
                }

                "Snow", "Lightning" -> {
                    DarkBlue
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

                "Clear" -> {
                    R.drawable.moon
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
                    R.drawable.partly_clear
                }

                "Intermittent clouds" -> {
                    R.drawable.mostly_sunny
                }

                "Mostly sunny" -> {
                    R.drawable.mostly_sunny
                }

                "Lightning" -> {
                    R.drawable.lightning
                }

                "Snow" -> {
                    R.drawable.snowy
                }

                "Sunny" -> {
                    R.drawable.suny
                }

                "Rain" -> {
                    R.drawable.rainy
                }

                "Showers" -> {
                    R.drawable.rainy
                }

                "Hazy sunshine" -> {
                    R.drawable.fog
                }

                "Hazy moonlight" -> {
                    R.drawable.partly_clear
                }

                else -> {
                    R.drawable.suny
                }
            }
        )

        //convert f to c temperature
        val temp = data.Temperature.Maximum.Value
        val temperature = ((temp - 32) / 1.8).toInt()

        Text(
            text = "$temperature°",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            ),
            color = when (data.Day.IconPhrase) {
                "Cloudy", "Rainy", "Lightning", "Sunny", "Mostly cloudy", "Mostly clear", "Intermittent clouds", "Mostly sunny", "Hazy sunshine" -> {
                    White
                }

                "Snow" -> {
                    DarkBlue
                }

                else -> {
                    White
                }
            },
            modifier = Modifier.padding(top = 8.dp , bottom = 24.dp)
        )
    }
}


@Composable
fun DailyForecastNightItem(data: DailyForecast, onceData: DailyForecast) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .background(noColor)
            .padding(end = 12.dp, start = 8.dp)
            .background(
                color =
                if (data.Date == onceData.Date) {
                    backgroundItem
                } else {
                    noColor
                },
                shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp)
            )
    ) {


        //convert f to c temperature
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
                "Cloudy", "Rainy", "Lightning", "Sunny", "Mostly cloudy", "Mostly clear", "Intermittent clouds", "Mostly sunny", "Hazy sunshine" -> {
                    White
                }

                "Snow" -> {
                    DarkBlue
                }

                else -> {
                    White
                }
            },
            modifier = Modifier.padding(bottom = 8.dp  , top = 24.dp)
        )



        AsyncImage(
            modifier = Modifier
                .size(52.dp)
                .padding(top = 4.dp),
            contentDescription = null,
            model = when (data.Night.IconPhrase) {
                "Cloudy" -> {
                    R.drawable.cloudy
                }

                "Clear" -> {
                    R.drawable.moon
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
                    R.drawable.partly_clear
                }

                "Intermittent clouds" -> {
                    R.drawable.mostly_sunny
                }

                "Mostly sunny" -> {
                    R.drawable.mostly_sunny
                }

                "Lightning" -> {
                    R.drawable.lightning
                }

                "Snow" -> {
                    R.drawable.snowy
                }

                "Sunny" -> {
                    R.drawable.suny
                }

                "Rain" -> {
                    R.drawable.rainy
                }

                "Showers" -> {
                    R.drawable.rainy
                }

                "Hazy sunshine" -> {
                    R.drawable.fog
                }

                "Hazy moonlight" -> {
                    R.drawable.partly_clear
                }

                else -> {
                    R.drawable.moon
                }
            }
        )

        //convert date to day
        val inputString = data.Date.substring(0, 10)
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = format.parse(inputString)
        val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(date!!)


        Text(
            text = dayOfWeek,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light
            ),
            modifier = Modifier.padding(8.dp),
            color = when (data.Day.IconPhrase) {
                "Cloudy", "Rainy", "Lightning", "Sunny", "Mostly cloudy", "Mostly clear", "Intermittent clouds", "Mostly sunny", "Hazy sunshine" -> {
                    White
                }

                "Snow" -> {
                    DarkBlue
                }

                else -> {
                    White
                }
            }
        )


    }

}


