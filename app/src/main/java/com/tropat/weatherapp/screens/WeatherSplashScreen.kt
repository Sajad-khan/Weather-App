package com.tropat.weatherapp.screens

import android.annotation.SuppressLint
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tropat.weatherapp.R
import com.tropat.weatherapp.navigation.WeatherScreens
import com.tropat.weatherapp.utils.Constants
import kotlinx.coroutines.delay

@Composable
fun WeatherSplashScreen(navController: NavController) {
    val scale = remember{
        Animatable(0f)
    }
    
    LaunchedEffect(key1 = true, block = {
        scale.animateTo(targetValue = .8f,
            animationSpec = tween(
                durationMillis = 1500,
                easing = {
                    OvershootInterpolator(8f)
                        .getInterpolation(it)
                }
            )
        )
        delay(2000L)
        navController.navigate(WeatherScreens.MainScreen.name+"/${Constants.defaultCity}")
    })
    SplashIcon(scale)
}

@Composable
private fun SplashIcon(scale: Animatable<Float, AnimationVector1D>) {
    Box(modifier = Modifier.fillMaxSize()
        .background(brush = Constants.brush),
        contentAlignment = Alignment.Center){
        Column(
            modifier = Modifier.background(color = Color.Transparent)
                .scale(scale.value),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .clip(CircleShape)
                    .size(250.dp)
                    .border(
                        width = 1.dp,
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.inverseSurface
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.weather),
                    contentDescription = "",
                    modifier = Modifier.size(200.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Yes! we've got the details",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,)
        }
    }
}

@SuppressLint("UnrememberedAnimatable")
@Preview
@Composable
fun SplashIconPreview(){
    val scale = Animatable(0f)
    SplashIcon(scale = scale)
}