package com.tropat.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(timestamp: Int): String{
    val sdf = SimpleDateFormat("EEE, MMM d")
    val date = java.util.Date(timestamp.toLong() * 1000)
    return sdf.format(date)
}

fun formatDateTime(timestamp: Int): String{
    val sdf = SimpleDateFormat("hh:mm:aa")
    val date = java.util.Date(timestamp.toLong() * 1000)
    return sdf.format(date)
}

fun formatDecimals(item: Double): String{
    return " %.0f".format(item)
}

fun getDayOfWeek(timestamp: Long): String {
    return SimpleDateFormat("EEEE", Locale.ENGLISH).format(timestamp * 1000).substring(0,3)
}