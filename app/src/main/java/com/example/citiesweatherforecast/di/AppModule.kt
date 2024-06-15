package com.example.citiesweatherforecast.di

import com.example.citiesweatherforecast.data.api.CitiesApi
import com.example.citiesweatherforecast.data.api.WeatherApi
import com.example.citiesweatherforecast.data.repository.CitiesRepository
import com.example.citiesweatherforecast.data.repository.WeatherRepository
import com.example.citiesweatherforecast.tools.Constants.BASE_URL_CITIES
import com.example.citiesweatherforecast.tools.Constants.BASE_URL_WEATHER
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideCitiesApi(): CitiesApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL_CITIES)
            .build()
            .create(CitiesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCitiesRepository(citiesApi: CitiesApi): CitiesRepository{
        return CitiesRepository(citiesApi)
    }

    @Singleton
    @Provides
    fun provideWeatherApi(): WeatherApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL_WEATHER)
            .build()
            .create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(weatherApi: WeatherApi): WeatherRepository{
        return WeatherRepository(weatherApi)
    }
}