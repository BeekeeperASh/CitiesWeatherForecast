package com.example.citiesweatherforecast.data.repository

import com.example.citiesweatherforecast.data.api.CitiesApi
import com.example.citiesweatherforecast.data.models.Cities
import com.example.citiesweatherforecast.tools.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CitiesRepository @Inject constructor(
    private val citiesApi: CitiesApi
) {

    suspend fun getCities(): Resource<Cities>{
        val response = try{
            citiesApi.getCities()
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(message = "Произошла ошибка")
        }
        return Resource.Success(response)
    }

}