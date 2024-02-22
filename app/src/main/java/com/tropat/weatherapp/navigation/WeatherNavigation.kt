package com.tropat.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tropat.weatherapp.screens.AboutScreenContent
import com.tropat.weatherapp.screens.favorites.FavoriteScreenContent
import com.tropat.weatherapp.screens.MainScreen
import com.tropat.weatherapp.screens.MainViewModel
import com.tropat.weatherapp.screens.SearchScreenContent
import com.tropat.weatherapp.screens.SettingsScreenContent
import com.tropat.weatherapp.screens.WeatherSplashScreen

@Composable
fun WeatherNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = WeatherScreens.SplashScreen.name){
        composable(WeatherScreens.SplashScreen.name){
            WeatherSplashScreen(navController = navController)
        }

        val route = WeatherScreens.MainScreen.name
        composable("$route/{city}",
            arguments = listOf(navArgument("city"){
                type = NavType.StringType
            })
        ){
            navBack ->
            navBack.arguments?.getString("city").let {
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, mainViewModel, city = it.toString())
            }
        }

        composable(WeatherScreens.SearchScreen.name){
            SearchScreenContent(navController = navController)
        }

        composable(WeatherScreens.FavoriteScreen.name){
            FavoriteScreenContent(navController = navController)
        }

        composable(WeatherScreens.SettingsScreen.name){
            SettingsScreenContent(navController = navController)
        }

        composable(WeatherScreens.AboutScreen.name){
            AboutScreenContent(navController = navController)
        }
    }
}

