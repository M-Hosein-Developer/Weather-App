package com.example.weatherapp.util

sealed class MyScreens(val route: String) {

    object HomeScreen : MyScreens("homeScreen")
    object NoInternetScreen : MyScreens("noInternetScreen")
    object SearchScreen : MyScreens("searchScreen")
    object DetailScreen : MyScreens("detailScreen")

}