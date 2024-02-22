package com.tropat.weatherapp.network

import com.tropat.weatherapp.model.Weather
import com.tropat.weatherapp.model.currentweather.CurrentWeather
import com.tropat.weatherapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") q: String,
        @Query("appid") apikey: String = Constants.API_KEY,
        @Query("units") units: String = "metric"

    ): Weather
    @GET(value = "data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q") q: String,
        @Query("appid") apikey: String = Constants.API_KEY,
        @Query("units") units: String = "metric"
    ): CurrentWeather
}
