package com.example.citiesweatherforecast.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citiesweatherforecast.data.models.Cities
import com.example.citiesweatherforecast.data.models.CitiesItem
import com.example.citiesweatherforecast.data.models.CityInfo
import com.example.citiesweatherforecast.data.repository.WeatherRepository
import com.example.citiesweatherforecast.tools.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectedCityViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val latitude = savedStateHandle.get<String>("latitude") ?: ""
    private val longitude = savedStateHandle.get<String>("longitude") ?: ""

    val cityInfo = mutableStateOf<CityInfo?>(null)
    var errorResult = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    init {
        cityInfoLoading()
    }

//    suspend fun getCityInfo(latitude: String, longitude: String) : Resource<CityInfo>{
//        return weatherRepository.getCityInfo(latitude, longitude)
//    }

    fun cityInfoLoading(){
        viewModelScope.launch {
            isLoading.value = true
            val result = weatherRepository.getCityInfo(latitude, longitude)
            when (result) {
                is Resource.Success -> {
                    cityInfo.value = result.data
                    errorResult.value = ""
                    isLoading.value = false
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {
                    errorResult.value = result.message!!
                    cityInfo.value = null
                    isLoading.value = false
                }
            }
        }
    }

}