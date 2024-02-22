package com.example.weatherapp.ui.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.LightBlue
import com.example.weatherapp.ui.theme.White

@Composable
fun NoInternetScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(LightBlue, White)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        NoInternet()

        Text(
            text = "Check your internet connection!",
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            ),
            color = Color.DarkGray,
            modifier = Modifier.padding()
        )

    }

}

@Composable
fun NoInternet() {

        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.wifi)
        )

        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )

}