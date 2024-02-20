package com.example.weatherapp.ui.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.weatherapp.model.dataClass.DailyForecast
import com.example.weatherapp.util.backgroundColor
import com.example.weatherapp.util.textColorWithIcon
import com.example.weatherapp.viewModel.MainViewModel

@Composable
fun SearchScreen(mainViewModel: MainViewModel, iconPhrase: String) {

    //ViewModel data
    val dailyResult = mainViewModel.search5DayForecast()
    val hourResult = mainViewModel.search12HourForecast()

    //Main Screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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

        SearchResult(dailyResult)

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
fun SearchResult(dailyResult: List<DailyForecast>) {
    LazyColumn(
        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp, start = 6.dp, end = 4.dp),
    ) {
        items(dailyResult.size) {
            SearchResultItem(dailyResult[it])
        }
    }
}

@Composable
fun SearchResultItem(dailyForecast: DailyForecast) {


}