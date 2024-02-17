package com.example.weatherapp.ui.feature

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
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
import com.example.weatherapp.util.MyScreens
import com.example.weatherapp.viewModel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavHostController,
    onClickedGetLocation: () -> Unit
) {

    //get data from view model
    val data5Day = homeViewModel.search5DayForecast()
    val data12Hour = homeViewModel.search12HourForecast()

    //init variable
    val iconPhrase = data5Day[0].Day.IconPhrase
    val temp = data5Day[0].Temperature.Maximum.Value
    val temperature = ((temp - 32) / 1.8).toInt()


    //screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors =
                    when (iconPhrase) {
                        "Cloudy", "Rainy" -> {
                            listOf(DarkBlue, DarkBlue)
                        }

                        "Snow", "Lightning", "Hazy sunshine" -> {
                            listOf(LightBlue, White)
                        }

                        "Sunny", "Mostly clear", "Intermittent clouds", "Mostly sunny" , "Partly sunny" , "Partly sunny w/ showers"-> {
                            listOf(gradiantBlue1, gradiantBlue2)
                        }

                        "Mostly cloudy" , "Flurries" , "Mostly cloudy w/ showers"-> {
                            listOf(DarkBlue, LightBlue)
                        }

                        else -> {
                            listOf(DarkBlue, DarkBlue)
                        }
                    }
                )
            )
    ) {

        //tool bar
        HomeScreenToolbar(
            homeViewModel.city.value,
            iconPhrase ,
            homeViewModel.isGettingLocation.value
        ){
            onClickedGetLocation.invoke()
            homeViewModel.getLocation()
        }

        //today status
        WeatherStatus(data5Day, data12Hour, iconPhrase, temperature){
            navController.navigate(MyScreens.DetailScreen.route + "/" + it)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenToolbar(cityName: String, iconPhrase: String, isGettingLocation: Boolean, onClickedGetLocation: () -> Unit) {

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
        },
        actions = {

            IconButton(
                onClick = { onClickedGetLocation.invoke() },
                modifier = Modifier.padding(end = 16.dp, top = 16.dp),
            ) {

                if (isGettingLocation){
                    DotsTyping()
                }else{
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_location_pin_24),
                        contentDescription = null ,
                        modifier = Modifier.size(30.dp),
                        tint =  when (iconPhrase) {
                            "Cloudy", "Rainy", "Lightning", "Sunny", "Mostly cloudy", "Mostly clear", "Intermittent clouds", "Mostly sunny" , "Mostly cloudy w/ showers"-> {
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
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherStatus(
    data5Day: List<DailyForecast>,
    data12Hour: List<Search12HourForecast>,
    iconPhrase: String, temperature: Int ,
    onClickedDayItem :(String) -> Unit
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

                "Partly sunny w/ showers" -> {
                    R.drawable.sun_cloudy
                }

                "Mostly cloudy w/ showers" -> {
                    R.drawable.sun_cloudy
                }

                "Partly cloudy w/ showers" -> {
                    R.drawable.mostly_sunny
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

                "Rainy" -> {
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
            text = iconPhrase,
            Modifier.padding(top = 16.dp),
            style = TextStyle(
                fontSize = 32.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            ),
            color = when (iconPhrase) {
                "Cloudy", "Rainy", "Lightning", "Sunny", "Mostly cloudy", "Mostly clear", "Intermittent clouds", "Mostly sunny" , "Mostly cloudy w/ showers" -> {
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

        WeatherForecastDay(data5Day, iconPhrase , onClickedDayItem)

        WeatherForecastHour(data12Hour, iconPhrase)
    }


}

//--------------------------------------------------------------------------------------------------
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherForecastDay(data: List<DailyForecast>, iconPhrase: String , onClickedDayItem :(String) -> Unit) {

    Column {

        Text(
            text = "5-day forecast",
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp),
            color = when (iconPhrase) {
                "Cloudy", "Rainy", "Lightning", "Sunny", "Mostly cloudy", "Mostly clear", "Intermittent clouds", "Mostly sunny", "Hazy sunshine" -> {
                    White
                }

                "Snow", "Lightning" -> {
                    DarkBlue
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
                ForecastDayItem(data[it] , onClickedDayItem)
            }

        }

    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastDayItem(data: DailyForecast , onClickedDayItem :(String) -> Unit) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .background(noColor)
            .padding(end = 12.dp, start = 10.dp)
            .clickable { onClickedDayItem.invoke(data.EpochDate.toString()) }
    ) {


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
            modifier = Modifier.padding(bottom = 8.dp),
            color = when (data.Night.IconPhrase) {
                "Cloudy", "Rainy", "Lightning", "Sunny", "Mostly cloudy", "Mostly clear", "Intermittent clouds", "Mostly sunny", "Hazy sunshine" -> {
                    White
                }

                "Snow" -> {
                    DarkBlue
                }

                else -> {
                    DarkBlue
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

                "Mostly cloudy w/ showers" -> {
                    R.drawable.mostly_sunny
                }

                "Clear" -> {
                    R.drawable.moon
                }

                "Partly sunny w/ showers" -> {
                    R.drawable.sun_cloudy
                }

                "Mostly cloudy" -> {
                    R.drawable.sun_cloudy
                }

                "Partly cloudy w/ showers" -> {
                    R.drawable.mostly_sunny
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
            modifier = Modifier.padding(top = 4.dp)
        )

    }

}

//--------------------------------------------------------------------------------------------------
@Composable
fun WeatherForecastHour(data: List<Search12HourForecast>, iconPhrase: String) {

    Column {

        Text(
            text = "12-hour forecast",
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp, top = 14.dp),
            color = when (iconPhrase) {
                "Cloudy", "Rainy", "Lightning", "Sunny", "Mostly cloudy", "Mostly clear", "Intermittent clouds", "Mostly sunny", "Hazy sunshine" -> {
                    White
                }

                "Snow", "Lightning", "Hazy sunshine" -> {
                    DarkBlue
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
                "Cloudy", "Rainy", "Lightning", "Sunny", "Mostly cloudy", "Mostly clear", "Intermittent clouds", "Mostly sunny", "Hazy sunshine" -> {
                    White
                }

                "Snow"-> {
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
            model = when (data.IconPhrase) {
                "Cloudy" -> {
                    R.drawable.cloudy
                }

                "Clear" -> {
                    R.drawable.moon
                }

                "Mostly cloudy" -> {
                    R.drawable.sun_cloudy
                }

                "Partly sunny w/ showers" -> {
                    R.drawable.sun_cloudy
                }

                "Mostly cloudy w/ showers" -> {
                    R.drawable.mostly_sunny
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

                "Partly cloudy w/ showers" -> {
                    R.drawable.mostly_sunny
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

                "Rainy" -> {
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
                "Cloudy", "Rainy", "Lightning", "Sunny", "Mostly cloudy", "Mostly clear", "Intermittent clouds", "Mostly sunny", "Hazy sunshine" -> {
                    White
                }

                "Snow", "Lightning", "Hazy sunshine" -> {
                    DarkBlue
                }

                else -> {
                    White
                }
            },
            modifier = Modifier.padding(top = 4.dp)
        )

    }

}

//--------------------------------------------------------------------------------------------------
@Composable
fun DotsTyping() {

    val dotSize = 10.dp
    val delayUnit = 350
    val maxOffset = 10f

    @Composable
    fun Dot(
        offset: Float
    ) = Spacer(
        Modifier
            .size(dotSize)
            .offset(y = -offset.dp)
            .background(
                color = Color.White,
                shape = CircleShape
            )
            .padding(start = 8.dp, end = 8.dp)
    )

    val infiniteTransition = rememberInfiniteTransition(label = "")

    @Composable
    fun animateOffsetWithDelay(delay: Int) = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = delayUnit * 4
                0f at delay with LinearEasing
                maxOffset at delay + delayUnit with LinearEasing
                0f at delay + delayUnit * 2
            }
        ), label = ""
    )

    val offset1 by animateOffsetWithDelay(0)
    val offset2 by animateOffsetWithDelay(delayUnit)
    val offset3 by animateOffsetWithDelay(delayUnit * 2)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = maxOffset.dp)
    ) {
        val spaceSize = 2.dp

        Dot(offset1)
        Spacer(Modifier.width(spaceSize))
        Dot(offset2)
        Spacer(Modifier.width(spaceSize))
        Dot(offset3)
    }
}