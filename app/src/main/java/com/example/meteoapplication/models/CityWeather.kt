package com.example.meteoapplication.models

data class CityWeather(
    val base: String,
    val main: Main,
    val name: String,
    val weather: List<Weather>
)