package com.example.citiesweatherforecast.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.citiesweatherforecast.domain.CitiesListViewModel
import com.example.citiesweatherforecast.ui.theme.*

@Composable
fun CitiesList(
    navController: NavController,
    viewModel: CitiesListViewModel = hiltViewModel()
) {

    val cities by remember {
        viewModel.cities
    }
    val errorResult by remember {
        viewModel.errorResult
    }
    val isLoading by remember {
        viewModel.isLoading
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background,
    ) {
        StickyLetterList(
            items = cities.map { ListItem(it.city, it.latitude, it.longitude) },
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            gutterWidth = 56.dp
        ) { item ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clip(shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                        .weight(1f)
                        .clickable {
                            navController.navigate("selected_city_screen/${item.city}/${item.latitude}/${item.longitude}")
                        },
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = item.city,
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal),
                        color = BlackText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
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
                viewModel.citiesLoading()
            }
        }
    }

}

@Composable
fun ErrorSection(
    error: String,
    onReload: () -> Unit
) {
    Column {
        Text(
            lineHeight = 20.sp,
            text = error,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(42.dp))
        Button(
            onClick = {
                onReload()
            },
            colors = ButtonDefaults.buttonColors(containerColor = PurpleDefault),
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
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

