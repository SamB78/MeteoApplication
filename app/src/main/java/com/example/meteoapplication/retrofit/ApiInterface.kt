package com.example.meteoapplication.retrofit

import com.example.meteoapplication.models.CityWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    suspend fun weather(@Query("id") zip: String, @Query("appid")apikey: String): CityWeather
}