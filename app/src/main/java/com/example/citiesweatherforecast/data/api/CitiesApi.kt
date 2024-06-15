package com.example.citiesweatherforecast.data.api

import com.example.citiesweatherforecast.data.models.Cities
import retrofit2.http.GET

interface CitiesApi {

    @GET("cities.json")
    suspend fun getCities(): Cities

}