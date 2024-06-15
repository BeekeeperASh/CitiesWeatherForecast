package com.example.citiesweatherforecast.data.repository

import com.example.citiesweatherforecast.data.api.WeatherApi
import com.example.citiesweatherforecast.data.models.CityInfo
import com.example.citiesweatherforecast.tools.Constants.API_KEY
import com.example.citiesweatherforecast.tools.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi
) {

    suspend fun getCityInfo(latitude: String, longitude: String): Resource<CityInfo> {
        val response = try{
            weatherApi.getCityInfo(latitude, longitude, API_KEY)
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(message = "Произошла ошибка")
        }
        return Resource.Success(response)
    }

}