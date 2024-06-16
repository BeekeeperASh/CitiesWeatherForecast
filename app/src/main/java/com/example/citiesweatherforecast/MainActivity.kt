package com.example.citiesweatherforecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.citiesweatherforecast.presentation.CitiesList
import com.example.citiesweatherforecast.presentation.SelectedCityScreen
import com.example.citiesweatherforecast.ui.theme.CitiesWeatherForecastTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            CitiesWeatherForecastTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "cities_list"){
                    composable("cities_list"){
                        CitiesList(navController)
                    }

                    composable(
                        "selected_city_screen/{city_name}/{latitude}/{longitude}",
                        arguments = listOf(
                            navArgument("city_name") {
                                type = NavType.StringType
                                this.defaultValue = "Moscow"
                            },
                            navArgument("latitude") {
                                type = NavType.StringType
                                this.defaultValue = "44.8783715"
                            },
                            navArgument("longitude") {
                                type = NavType.StringType
                                this.defaultValue = "39.190172"
                            }
                        )
                    ) {
                        val cityName = remember {
                            it.arguments?.getString("city_name")
                        }
                        SelectedCityScreen(
                            cityName = cityName ?: ""
                        )
                    }
                }

            }
        }
    }
}