package ru.nikita.weatherappdiplom.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.nikita.weatherappdiplom.dto.WeatherModel

interface WeatherApiService {

    @GET("forecast.json")
    suspend fun getWeatherData(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("days") days: String,
        @Query("aqi") aqi: String,
        @Query("alerts") alerts: String,
        @Query("lang") lang: String,
    ): Response<WeatherModel>
}