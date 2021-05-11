package com.example.meteoapplication.repository

import com.example.meteoapplication.retrofit.RetrofitClient

class WeatherRepository {

    // Raccourci pris l'APIKEY ne devrait pas se trouver ici normalement
    private val apiKey = "6820fcda05c55b7b2733ea210e13b0a9"

    suspend fun getWeatherFromIdCitiesList(cityId: String) =
        RetrofitClient.apiInterface.weather(cityId, apiKey)
}