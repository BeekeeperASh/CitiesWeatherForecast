package com.example.citiesweatherforecast.presentation

import android.health.connect.datatypes.units.Temperature
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.citiesweatherforecast.data.models.CityInfo
import com.example.citiesweatherforecast.domain.SelectedCityViewModel
import com.example.citiesweatherforecast.tools.Resource
import com.example.citiesweatherforecast.ui.theme.Background
import com.example.citiesweatherforecast.ui.theme.PurpleDefault
import kotlin.math.roundToInt

@Composable
fun SelectedCityScreen(
    cityName: String,
    viewModel: SelectedCityViewModel = hiltViewModel()
){

//    val cityInfo = produceState<Resource<CityInfo>>(initialValue = Resource.Loading()) {
//        value = viewModel.getCityInfo(latitude, longitude)
//    }.value

    val cityInfo by remember {
        viewModel.cityInfo
    }

    val errorResult by remember {
        viewModel.errorResult
    }
    val isLoading by remember {
        viewModel.isLoading
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, bottom = 36.dp, start = 16.dp, end = 16.dp)){
            if (cityInfo != null) {
                CityTemperature(
                    temp = cityInfo!!.main.temp,
                    cityName = cityName,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
                Button(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    colors = ButtonDefaults.buttonColors(containerColor = PurpleDefault),
                    onClick = { viewModel.cityInfoLoading() }
                ) {
                    Text(
                        text = "Обновить",
                        style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium),
                        lineHeight = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Background
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(48.dp), color = PurpleDefault)
            } else if (errorResult.isNotEmpty()) {
                ErrorSection(error = errorResult) {
                    viewModel.cityInfoLoading()
                }
            }
        }
    }

}

@Composable
fun CityTemperature(temp: Double, cityName: String, modifier: Modifier){
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "${temp.roundToInt()}°C",
            fontWeight = FontWeight.Normal,
            fontSize = 57.sp,
            lineHeight = 64.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = cityName,
            fontWeight = FontWeight.Normal,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}