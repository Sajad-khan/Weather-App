package com.tropat.weatherapp.screens.favorites

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tropat.weatherapp.navigation.WeatherScreens
import com.tropat.weatherapp.room.Favorite
import com.tropat.weatherapp.utils.Constants
import com.tropat.weatherapp.widgets.WeatherAppBar

@Composable
fun FavoriteScreenContent(navController: NavController){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(brush = Constants.brush),
        contentAlignment = Alignment.Center){
        Scaffold(
            topBar = {WeatherAppBar(navController = navController, title = "Favorites", isMainScreen = false)},
            containerColor = Color.Transparent
        ) {
            FavoriteScreenMainContent(it, navController)
        }
    }
}

@Composable
fun FavoriteScreenMainContent(it: PaddingValues, navController: NavController) {
    val favoriteViewModel: FavoriteViewModel = hiltViewModel()
    val favoriteList = favoriteViewModel.favList.collectAsState().value
    if(favoriteList.isEmpty()){
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "No favorite cities yet!")
        }
    }
   else{
        LazyColumn(modifier = Modifier
            .padding(paddingValues = it)
            .padding(10.dp)
            .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)){
            items(favoriteList){
                FavoriteItem(it, navController)
            }
        }
    }
}


@Composable
fun FavoriteItem(favoriteItem: Favorite = Favorite("Srinagar", "India"), navController: NavController){
    val favoriteViewModel: FavoriteViewModel = hiltViewModel()
    val context = LocalContext.current
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(brush = Constants.brush)
        .border(.01.dp, Color.White, shape = RoundedCornerShape(10.dp))
        .clickable { navController.navigate(WeatherScreens.MainScreen.name+"/${favoriteItem.city}") },
        contentAlignment = Alignment.Center
    ){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = "${favoriteItem.city}, ${favoriteItem.country}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f))
            IconButton(onClick = {
                favoriteViewModel.deleteFavorite(favoriteItem)
                Toast.makeText(context, "${favoriteItem.city} removed from favorites!", Toast.LENGTH_SHORT).show()
            }) {
                Icon(imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Button")
            }
        }
    }
}
