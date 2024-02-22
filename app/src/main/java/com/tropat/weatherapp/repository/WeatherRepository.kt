package com.tropat.weatherapp.repository

import android.util.Log
import com.tropat.weatherapp.data.DataOrException
import com.tropat.weatherapp.model.Weather
import com.tropat.weatherapp.model.currentweather.CurrentWeather
import com.tropat.weatherapp.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi){
    suspend fun getWeather(cityQuery: String):
            DataOrException<Weather, Boolean, Exception>{
        val dailyWeatherResponse = try{
            api.getWeather(cityQuery)
        }
        catch (e: Exception){
            Log.d("KHA","weather: $e")
            return DataOrException(e = e)
        }
        Log.d("DAILY", "Data is : $dailyWeatherResponse")
        return DataOrException(data = dailyWeatherResponse)
    }

    suspend fun getCurrentWeather(cityQuery: String):
            DataOrException<CurrentWeather, Boolean, Exception>{
        val currentWeatherResponse = try{
            api.getCurrentWeather(cityQuery)
        }
        catch (e: Exception){
            Log.d("KHA","weather: $e")
            return DataOrException(e = e)
        }
        Log.d("CURR", "Data is : $currentWeatherResponse")
        return DataOrException(data = currentWeatherResponse)
    }

}