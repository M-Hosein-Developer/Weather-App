package com.example.weatherapp.ui.feature

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.weatherapp.R
import com.example.weatherapp.model.dataClass.DailyForecast
import com.example.weatherapp.ui.theme.backgroundItem
import com.example.weatherapp.util.MyScreens
import com.example.weatherapp.util.backgroundColor
import com.example.weatherapp.util.convertFarenToCele
import com.example.weatherapp.util.dateToDay
import com.example.weatherapp.util.imageDayStatus
import com.example.weatherapp.util.textColorWithIcon
import com.example.weatherapp.viewModel.MainViewModel

@Composable
fun SearchScreen(mainViewModel: MainViewModel, navController: NavHostController, iconPhrase: String) {

    //ViewModel data
    val dailyResult =  mainViewModel.search5DayForecast()

    //Main Screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = backgroundColor(iconPhrase)
                )
            )
    ) {

        SearchBox(
            edtValue = mainViewModel.search.value,
            icon = Icons.Default.Search,
            hint = "Search",
            iconPhrase
        ) {
            mainViewModel.search.value = it
            mainViewModel.getDataFromSearch()
        }

        SearchResult(dailyResult , mainViewModel){
            navController.navigate(MyScreens.DetailScreen.route + "/" + it)
        }

    }
}

//Search toolbar
@Composable
fun SearchBox(edtValue: String, icon: ImageVector, hint: String, iconPhrase: String, onValueChanges: (String) -> Unit) {
    OutlinedTextField(
        label = { Text(hint , color =  textColorWithIcon(iconPhrase)) },
        value = edtValue,
        singleLine = true,
        onValueChange = onValueChanges,
        placeholder = { Text(hint , color =  textColorWithIcon(iconPhrase)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
        shape = ShapeDefaults.Medium,
        leadingIcon = { Icon(imageVector = icon, contentDescription = null , tint = textColorWithIcon(iconPhrase) ) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = textColorWithIcon(iconPhrase),
            unfocusedBorderColor = textColorWithIcon(iconPhrase),
            focusedTextColor =  textColorWithIcon(iconPhrase),
        ),
    )

}

//Search Result
@Composable
fun SearchResult(dailyResult: List<DailyForecast>, mainViewModel: MainViewModel , onClickedItem :(String) -> Unit) {

    if (dailyResult.size >= 2) {
        LazyColumn {
            items(dailyResult.size) {
                SearchResultItem(dailyResult[it], mainViewModel, onClickedItem)
                Log.v("testSearch", dailyResult[it].EpochDate.toString())
            }
        }
    }else{
        Loading()
    }
}

@Composable
fun SearchResultItem(dailyForecast: DailyForecast, mainViewModel: MainViewModel , onClickedItem :(String) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(horizontal = 16.dp)
            .padding(top = 12.dp, bottom = 12.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundItem)
            .clickable { onClickedItem.invoke(dailyForecast.EpochDate.toString()) },
         horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            modifier = Modifier
                .size(110.dp)
                .padding(8.dp)
                .padding(top = 12.dp),
            model = imageDayStatus(dailyForecast.Day.IconPhrase),
            contentDescription = null
        )

        Text(
            text = mainViewModel.cityName.value,
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            color = textColorWithIcon(dailyForecast.Day.IconPhrase),
            modifier = Modifier.padding(top = 18.dp , bottom = 18.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Text(
                text = convertFarenToCele(dailyForecast.Temperature.Maximum.Value),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center
                ),
                color = textColorWithIcon(dailyForecast.Day.IconPhrase)
            )

            Text(
                text = dateToDay(dailyForecast.Date),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                color = textColorWithIcon(dailyForecast.Day.IconPhrase)
            )

            Text(
                text = convertFarenToCele(dailyForecast.Temperature.Minimum.Value),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center
                ),
                color = textColorWithIcon(dailyForecast.Day.IconPhrase)
            )
        }
    }
}

//loading animation
@Composable
fun Loading() {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading_anime)
    )

    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.size(150.dp),
    )
}