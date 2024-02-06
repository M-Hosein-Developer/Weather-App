package com.example.weatherapp.util

sealed class MyScreens(val route: String) {

    object HomeScreen : MyScreens("homeScreen")
    object NewsScreen : MyScreens("newsScreen")
    object SearchScreen : MyScreens("searchScreen")
    object DetailScreen : MyScreens("detailScreen")

}