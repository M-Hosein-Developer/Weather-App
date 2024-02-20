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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.weatherapp.model.dataClass.DailyForecast
import com.example.weatherapp.model.dataClass.Search12HourForecast
import com.example.weatherapp.ui.theme.noColor
import com.example.weatherapp.util.MyScreens
import com.example.weatherapp.util.backgroundColor
import com.example.weatherapp.util.imageDayStatus
import com.example.weatherapp.util.textColorWithIcon
import com.example.weatherapp.viewModel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    navController: NavHostController,
    onClickedGetLocation: () -> Unit
) {

    //get data from view model
    val data5Day = mainViewModel.search5DayForecast()
    val data12Hour = mainViewModel.search12HourForecast()

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
                    colors = backgroundColor(iconPhrase)
                )
            )
    ) {

        //tool bar
        HomeScreenToolbar(
            mainViewModel.city.value,
            iconPhrase,
            mainViewModel.isGettingLocation.value,
            {
                onClickedGetLocation.invoke()
                mainViewModel.getLocation()
            },
            {
                navController.navigate(MyScreens.SearchScreen.route + "/" + it)
            }
        )

        //today status
        WeatherStatus(data5Day, data12Hour, iconPhrase, temperature){
            navController.navigate(MyScreens.DetailScreen.route + "/" + it)
        }
    }
}


//Home Toolbar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenToolbar(
    cityName: String,
    iconPhrase: String,
    isGettingLocation: Boolean,
    onClickedGetLocation: () -> Unit,
    onClickedSearch: (String) -> Unit
) {

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
                color = textColorWithIcon(iconPhrase)
            )
        },
        actions = {

            IconButton(
                onClick = { onClickedSearch.invoke(iconPhrase) },
                modifier = Modifier.padding(end = 4.dp, top = 20.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = textColorWithIcon(iconPhrase)
                )
            }

            IconButton(
                onClick = { onClickedGetLocation.invoke() },
                modifier = Modifier.padding(end = 16.dp, top = 16.dp),
            ) {

                if (isGettingLocation) {
                    DotsTyping()
                } else {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = textColorWithIcon(iconPhrase)
                    )
                }
            }

        }
    )
}

//Big screen first status
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
            model = imageDayStatus(data5Day[0].Day.IconPhrase)
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
            color = textColorWithIcon(iconPhrase)
        )

        Text(
            text = "$temperature°",
            Modifier.padding(top = 14.dp, bottom = 14.dp),
            style = TextStyle(
                fontSize = 42.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            ),
            color = textColorWithIcon(iconPhrase)
        )

        WeatherForecastDay(data5Day, iconPhrase , onClickedDayItem)

        WeatherForecastHour(data12Hour, iconPhrase)
    }


}

//--------------------------------------------------------------------------------------------------

//Daily forecast
@Composable
fun WeatherForecastDay(data: List<DailyForecast>, iconPhrase: String , onClickedDayItem :(String) -> Unit) {

    Column {

        Text(
            text = "5-day forecast",
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp),
            color = textColorWithIcon(iconPhrase),
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
            color = textColorWithIcon(data.Day.IconPhrase)
        )

        AsyncImage(
            modifier = Modifier
                .size(52.dp)
                .padding(top = 4.dp),
            contentDescription = null,
            model = imageDayStatus(data.Day.IconPhrase)
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
            color = textColorWithIcon(data.Day.IconPhrase),
            modifier = Modifier.padding(top = 4.dp)
        )

    }

}

//--------------------------------------------------------------------------------------------------

//Hourly forecast
@Composable
fun WeatherForecastHour(data: List<Search12HourForecast>, iconPhrase: String) {

    Column {

        Text(
            text = "12-hour forecast",
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp, top = 14.dp),
            color = textColorWithIcon(iconPhrase),
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
            color = textColorWithIcon(data.IconPhrase)
        )

        AsyncImage(
            modifier = Modifier
                .size(52.dp)
                .padding(top = 4.dp),
            contentDescription = null,
            model = imageDayStatus(data.IconPhrase)
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
            color = textColorWithIcon(data.IconPhrase),
            modifier = Modifier.padding(top = 4.dp)
        )

    }

}

//--------------------------------------------------------------------------------------------------

//Dot function for location event
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