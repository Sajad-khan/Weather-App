package com.tropat.weatherapp.screens.favorites

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tropat.weatherapp.model.currentweather.CurrentWeather
import com.tropat.weatherapp.repository.WeatherDbRepository
import com.tropat.weatherapp.room.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDbRepository)
    : ViewModel(){
    private var _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()
    val isFavorite = mutableStateOf(false)
    init {
        viewModelScope.launch {
            repository.getFavorites().distinctUntilChanged()
                .collect{
                    if(it.isEmpty()){
                        Log.d("EMP", "Favorite list is empty.")
                    }
                    else{
                        _favList.value = it
                        Log.d("NE", "Favorite list is $it")
                    }
                }
        }
    }


    fun insertFavorite(favorite: Favorite)
        = viewModelScope.launch { repository.insertFavorite(favorite) }
    fun deleteFavorite(favorite: Favorite)
        = viewModelScope.launch { repository.deleteFavorite(favorite) }
    fun cityIsInFavorites(city: String){
        viewModelScope.launch {
            isFavorite.value = repository.cityIsInFavorites(city)
        }
    }
    fun performFavoriteOperation(favorite: Favorite){
        if(isFavorite.value){
            deleteFavorite(favorite)
        }
        else insertFavorite(favorite)
        isFavorite.value = !isFavorite.value
    }
}