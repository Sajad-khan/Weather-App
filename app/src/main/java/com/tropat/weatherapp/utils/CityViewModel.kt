package com.tropat.weatherapp.utils

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CityViewModel: ViewModel() {

    var city by mutableStateOf("Jammu")

    fun getCurrentCity(): String{
        return city
    }
}