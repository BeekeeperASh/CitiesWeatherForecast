package com.example.citiesweatherforecast.domain

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citiesweatherforecast.data.models.CitiesItem
import com.example.citiesweatherforecast.data.repository.CitiesRepository
import com.example.citiesweatherforecast.tools.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.internal.filterList
import javax.inject.Inject

@HiltViewModel
class CitiesListViewModel @Inject constructor(
    private val repository: CitiesRepository
) : ViewModel() {

    var cities = mutableStateOf<List<CitiesItem>>(listOf())
    var errorResult = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    init {
        citiesLoading()
    }

    fun citiesLoading(){
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getCities()
            when (result) {
                is Resource.Success -> {
                    val citiesList = result.data!!.filter { it.city.trim() != "" }.sortedBy { it.city }
                    cities.value = citiesList
                    errorResult.value = ""
                    isLoading.value = false
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {
                    errorResult.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }

}