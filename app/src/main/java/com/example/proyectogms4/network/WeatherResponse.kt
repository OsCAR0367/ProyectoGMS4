package com.example.proyectogms4.network


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "c2313ec635f175efac05b24a1e421ec3"
const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

data class WeatherResponse(
    val main: Main,
    val wind: Wind
)

data class Main(
    val temp: Float,
    val humidity: Int
)

data class Wind(
    val speed: Float
)

interface WeatherService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String = API_KEY,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}

object RetrofitInstance {
    val api: WeatherService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
    }
}