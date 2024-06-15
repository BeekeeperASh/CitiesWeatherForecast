package com.example.citiesweatherforecast.data.api

import com.example.citiesweatherforecast.data.models.CityInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather?exclude=minutely%2Chourly%2Cdaily%2Calerts&units=metric") //
    suspend fun getCityInfo(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("appid") apiKey: String
    ) : CityInfo

}