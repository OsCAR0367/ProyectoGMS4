package com.example.proyectogms4.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.proyectogms4.R
import com.example.proyectogms4.network.Forecast
import com.example.proyectogms4.network.RetrofitInstance
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(navController: NavController) {
    var temperature by remember { mutableStateOf("") }
    var humidity by remember { mutableStateOf("") }
    var windSpeed by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var visibility by remember { mutableStateOf("") }
    var pressure by remember { mutableStateOf("") }
    var forecast by remember { mutableStateOf<List<Forecast>>(emptyList()) }
    var weatherData by remember { mutableStateOf<List<Entry>>(listOf(
        Entry(0f, 25f),
        Entry(1f, 26f),
        Entry(2f, 24f),
        Entry(3f, 27f),
        Entry(4f, 23f)
    )) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val weatherResponse = RetrofitInstance.api.getWeather("Arequipa")
                temperature = "${weatherResponse.main.temp}°C"
                humidity = "${weatherResponse.main.humidity}%"
                windSpeed = "${weatherResponse.wind.speed} km/h"
                description = weatherResponse.weather.firstOrNull()?.description ?: "N/A"
                visibility = "${weatherResponse.visibility} m"
                pressure = "${weatherResponse.main.pressure} hPa"

                val forecastResponse = RetrofitInstance.api.getForecast("Arequipa")
                forecast = forecastResponse.list
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Clima Hoy") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .padding(16.dp)
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(
                        text = "Información del Clima en Arequipa ahora",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.DarkGray
                    )
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        WeatherInfoCard("Temperatura: $temperature", R.drawable.ic_mindful, Modifier.weight(1f))
                        WeatherInfoCard("Humedad: $humidity", R.drawable.ic_water, Modifier.weight(1f))
                    }
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        WeatherInfoCard("Viento: $windSpeed", R.drawable.ic_back_24, Modifier.weight(1f))
                        WeatherInfoCard("Descripción: $description", R.drawable.ic_test, Modifier.weight(1f))
                    }
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        WeatherInfoCard("Visibilidad: $visibility", R.drawable.ic_play, Modifier.weight(1f))
                        WeatherInfoCard("Presión: $pressure", R.drawable.ic_burn_calories, Modifier.weight(1f))
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    AndroidView(factory = { context ->
                        LineChart(context).apply {
                            val dataSet = LineDataSet(weatherData, "Temperatura")
                            data = LineData(dataSet)
                            invalidate()
                        }
                    }, modifier = Modifier.fillMaxWidth().height(200.dp))
                }
                item {
                    Text(
                        text = "Pronóstico del Clima",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.DarkGray
                    )
                }
                items(forecast.size) { index ->
                    val item = forecast[index]
                    WeatherInfoCard(
                        info = "${item.dt_txt}: ${item.main.temp}°C, ${item.weather.firstOrNull()?.description ?: "N/A"}",
                        iconRes = R.drawable.ic_weather
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherInfoCard(info: String, iconRes: Int, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = info,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}