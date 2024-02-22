package com.tropat.weatherapp.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tropat.weatherapp.R
import com.tropat.weatherapp.data.DataOrException
import com.tropat.weatherapp.model.Weather
import com.tropat.weatherapp.model.WeatherItem
import com.tropat.weatherapp.model.currentweather.CurrentWeather
import com.tropat.weatherapp.navigation.WeatherScreens
import com.tropat.weatherapp.room.Favorite
import com.tropat.weatherapp.screens.favorites.FavoriteViewModel
import com.tropat.weatherapp.utils.Constants
import com.tropat.weatherapp.utils.getDayOfWeek

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String
){
    val dailyWeatherData =
        produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(
                loading = true
            )
        ) {
            value = mainViewModel.getWeather(city)
        }.value

    val currentWeatherData =
        produceState<DataOrException<CurrentWeather, Boolean, Exception>>(
            initialValue = DataOrException(
                loading = true
            )
        ) {
            value = mainViewModel.getCurrentWeather(city)
        }.value
    if(dailyWeatherData.loading == true || currentWeatherData.loading == true) CircularProgressIndicator()
    else if(dailyWeatherData.data != null && currentWeatherData.data != null){
        MainScaffold(dailyWeatherData.data!!, currentWeatherData.data!!, navController)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScaffold(weather: Weather, currentWeather: CurrentWeather, navController: NavController){
    Scaffold{
        paddingValue ->
        MainContent(weather,currentWeather, paddingValue, navController)
    }
}

@Composable
fun MainContent(weather: Weather,
                currentWeather: CurrentWeather,
                paddingValue: PaddingValues,
                navController: NavController) {
    val imageId = getImageId(currentWeather.weather[0].icon)
    var showDialog = remember{
        mutableStateOf(false)
    }
    Box(
        Modifier
            .fillMaxSize()
            .background(brush = Constants.brush)){
        if(showDialog.value){
            ShowOptionsDialog(showDialog = showDialog, navController = navController)
        }
        Column(
            Modifier
                .padding(paddingValue)
                .fillMaxWidth()
                .background(color = Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally){
            CityName(navController, currentWeather) { showDialog.value = !showDialog.value }
            Spacer(modifier = Modifier.height(5.dp))
            WeatherImage(imageId)
            TemperatureText(currentWeather)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = currentWeather.weather[0].main,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(5.dp))
            MinAndMaxTemperature(weather)
            Image(painter = painterResource(id = R.drawable.house_bg),
                contentDescription = "weather Image",
                modifier = Modifier.size(height = 198.dp, width = 336.dp),
                contentScale = ContentScale.Fit)
            ForecastBox(weather)
        }
    }
}

@Composable
private fun ForecastBox(weather: Weather) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .clip(shape = RoundedCornerShape(36.dp))
            .background(brush = Constants.brush),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "7-Day Forecast", style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Divider(Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(10.dp))
            WeatherForecastLazyRow(weather)
        }
    }
}

@Composable
private fun MinAndMaxTemperature(weather: Weather) {
    Row {
        Text(
            text = "Max: ${weather.list[0].temp.max.toInt()}\u2103",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = "Min: ${weather.list[0].temp.min.toInt()}\u2103",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun TemperatureText(currentWeather: CurrentWeather) {
    Text(
        text = "${currentWeather.main.temp.toInt()}\u2103",
        style = MaterialTheme.typography.displayLarge
    )
}

@Composable
private fun WeatherImage(imageId: Int) {
    Image(
        painter = painterResource(id = imageId),
        contentDescription = "weather Image",
        modifier = Modifier.size(150.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun CityName(
    navController: NavController,
    currentWeather: CurrentWeather,
    optionsButtonClicked: () -> Unit
) {
    var addToFavoriteListener by remember {
        mutableStateOf(false)
    }
    val favoriteViewModel: FavoriteViewModel = hiltViewModel()
    val isFavorite by remember {
        mutableStateOf(favoriteViewModel.isFavorite)
    }
    val city = currentWeather.name
    val country = currentWeather.sys.country
    val context = LocalContext.current
    favoriteViewModel.cityIsInFavorites(city)
    Box(modifier = Modifier
        .width(340.dp)
        .clickable { navController.navigate(WeatherScreens.SearchScreen.name) }
        .height(45.dp)
        .padding(top = 5.dp, bottom = 5.dp)
        .background(Color.Transparent)
        .border(width = .5.dp, color = Color.White, shape = RoundedCornerShape(30.dp))
        .clip(shape = RoundedCornerShape(30.dp)),
        contentAlignment = Alignment.Center) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically){
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            }
            Text(modifier = Modifier.weight(1f), text = currentWeather.name, style = MaterialTheme.typography.titleMedium)
            IconButton(onClick = {
                favoriteViewModel.performFavoriteOperation(Favorite(city, country))
                if(!isFavorite.value){
                    Toast.makeText(context, "$city removed from favorites!", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(context, "$city added to favorites!", Toast.LENGTH_SHORT).show()
                }
                addToFavoriteListener = !addToFavoriteListener
            }) {
                if(isFavorite.value){
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "",
                        tint = Color.Red.copy(alpha = 1f))
                }
                else{
                    Icon(imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "",)
                }
            }
            IconButton(onClick = {optionsButtonClicked.invoke()}) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "")
            }
        }
    }
}

@Composable
fun ShowOptionsDialog(showDialog: MutableState<Boolean>, navController: NavController) {
    var isExpanded by remember {
        mutableStateOf(showDialog)
    }
    val items = listOf("Favorites", "About", "Settings")
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.TopEnd)
        .absolutePadding(top = 65.dp, right = 45.dp)) {
        DropdownMenu(expanded = isExpanded.value,
            onDismissRequest = {
                isExpanded.value = false
                showDialog.value = false},
            modifier = Modifier
                .width(140.dp)
                .background(brush = Constants.brush)) {
            items.forEachIndexed { _, s ->
                DropdownMenuItem(text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = when(s){
                            "Favorites" -> Icons.Default.FavoriteBorder
                            "About" -> Icons.Default.Info
                            else -> Icons.Default.Settings
                        },
                            contentDescription = "Icon")
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = s)
                    }
                },
                    onClick = {
                        when(s){
                            "Favorites" -> navController.navigate(WeatherScreens.FavoriteScreen.name)
                            "About" -> navController.navigate(WeatherScreens.AboutScreen.name)
                            else -> navController.navigate(WeatherScreens.SettingsScreen.name)
                        }
                    })
            }

        }
    }
}

@Composable
fun WeatherForecastLazyRow(weather: Weather) {
    LazyRow(contentPadding = PaddingValues(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)){
        val weatherList = weather.list
        items(weatherList){weatherItem ->
            DailyWeatherItem(weatherItem = weatherItem)
        }
    }
}

@Composable
fun DailyWeatherItem(
    weatherItem: WeatherItem
){
    Box(modifier = Modifier
        .width(110.dp)
        .height(290.dp)
        .clip(shape = RoundedCornerShape(36.dp))
        .background(brush = Constants.brush)){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "${weatherItem.temp.day.toInt()}℃", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(20.dp))
            Image(painter = painterResource(id = getImageId(weatherItem.weather[0].icon)),
                modifier = Modifier.size(60.dp),
                contentDescription = "icon")
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = getDayOfWeek(weatherItem.dt.toLong()), style = MaterialTheme.typography.titleLarge)
        }
    }
}

fun getImageId(icon: String): Int{
    when(icon){
        "01d" -> return R.drawable.clear_sky_day
        "01n" -> return R.drawable.clear_sky_night
        "02d" -> return R.drawable.few_clouds_day
        "02n" -> return R.drawable.few_clouds_night
        "03d" -> return R.drawable.clouds
        "03n" -> return R.drawable.clouds
        "04d" -> return R.drawable.clouds
        "04n" -> return R.drawable.clouds
        "09d" -> return R.drawable.rain
        "09n" -> return R.drawable.rain
        "10d" -> return R.drawable.rain
        "10n" -> return R.drawable.rain
        "11d" -> return R.drawable.thunder_storm
        "11n" -> return R.drawable.thunder_storm
        "13d" -> return R.drawable.snow
        "13n" -> return R.drawable.snow
        "50d" -> return R.drawable.mist
        "50n" -> return R.drawable.mist
    }
    return R.drawable.few_clouds_day
}

