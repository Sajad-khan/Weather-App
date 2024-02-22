package com.tropat.weatherapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tropat.weatherapp.navigation.WeatherScreens
import com.tropat.weatherapp.utils.Constants
import com.tropat.weatherapp.widgets.WeatherAppBar


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreenContent(navController: NavController){
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var city by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        Modifier
            .background(brush = Constants.brush)
            .fillMaxSize()
    ) {
        WeatherAppBar(title = "Search",
            isMainScreen = false,
            navController = navController)
        Divider(Modifier.fillMaxWidth())
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 5.dp),
            value = city,
            placeholder = { Text("Enter City") },
            onValueChange = {
                city = it
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search icon"
                )
            },
            maxLines = 1,
            shape = RoundedCornerShape(30.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    if(city.trim().isNotEmpty()){
                        navController.navigate(WeatherScreens.MainScreen.name+"/$city")
                    }
                }
            ),
        )
    }
}

