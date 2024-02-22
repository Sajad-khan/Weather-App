package com.tropat.weatherapp.repository

import com.tropat.weatherapp.room.Favorite
import com.tropat.weatherapp.room.WeatherDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {
    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()
    suspend fun cityIsInFavorites(city: String): Boolean = weatherDao.cityIsInFavorites(city)
}