package com.example.weatherapp.util

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.DarkBlue
import com.example.weatherapp.ui.theme.LightBlue
import com.example.weatherapp.ui.theme.White
import com.example.weatherapp.ui.theme.gradiantBlue1
import com.example.weatherapp.ui.theme.gradiantBlue2
import kotlinx.coroutines.CoroutineExceptionHandler

val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.v("error" , "Error -> " + throwable.message)
}

fun textColorWithIcon(icon : String) : Color{
    return when (icon) {
        "Cloudy", "Rainy", "Lightning", "Sunny", "Mostly cloudy", "Mostly clear", "Intermittent clouds", "Mostly sunny", "Mostly cloudy w/ showers" -> {
            White
        }

        "Snow", "Hazy sunshine" -> {
            DarkBlue
        }

        else -> {
            White
        }
    }
}

fun backgroundColor(icon : String) : List<Color>{
    return when (icon) {
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
}

fun imageDayStatus(icon : String) : Int{
    return when (icon) {
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
            R.drawable.background1
        }
    }
}