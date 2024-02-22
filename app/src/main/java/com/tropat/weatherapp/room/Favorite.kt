package com.tropat.weatherapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "fav_table")
data class Favorite(
    @Nonnull
    @PrimaryKey
    val city: String,
    val country: String
)