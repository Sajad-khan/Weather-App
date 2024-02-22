package com.tropat.weatherapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.tropat.weatherapp.utils.Constants
import com.tropat.weatherapp.widgets.WeatherAppBar

@Composable
fun SettingsScreenContent(navController: NavController){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(brush = Constants.brush),
        contentAlignment = Alignment.Center){
        Scaffold(
            topBar = { WeatherAppBar(navController = navController, title = "Settings", isMainScreen = false) },
            containerColor = Color.Transparent
        ) {
            SettingsScreenMainContent(it)
        }
    }
}

@Composable
fun SettingsScreenMainContent(it: PaddingValues) {
    Column(modifier = Modifier.padding(it)
        .background(Color.Transparent)) {
        Text(text = "This is Settings Screen!")
    }
}