package com.tropat.weatherapp.screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tropat.weatherapp.data.DataOrException
import com.tropat.weatherapp.model.Weather
import com.tropat.weatherapp.model.currentweather.CurrentWeather
import com.tropat.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel(){
    suspend fun getWeather(city: String):
            DataOrException<Weather, Boolean, Exception>{
        return repository.getWeather(city)
    }

    suspend fun getCurrentWeather(city: String):
            DataOrException<CurrentWeather, Boolean, Exception>{
        return repository.getCurrentWeather(city)
    }
}