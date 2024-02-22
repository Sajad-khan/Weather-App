package com.tropat.weatherapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.tropat.weatherapp.R
import com.tropat.weatherapp.utils.Constants
import com.tropat.weatherapp.widgets.WeatherAppBar

@Composable
fun AboutScreenContent(navController: NavController){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(brush = Constants.brush),
        contentAlignment = Alignment.Center){
        Scaffold(
            topBar = { WeatherAppBar(navController = navController, title = "About", isMainScreen = false) },
            containerColor = Color.Transparent
        ) {
            AboutScreenMainContent(it)
        }
    }
}

@Composable
fun AboutScreenMainContent(it: PaddingValues) {
    Column(modifier = Modifier
        .padding(it)
        .fillMaxSize()
        .background(Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(id = R.string.about_app),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold)
        Text(text = "Data provided by ${stringResource(id = R.string.api_info)}")
    }
}