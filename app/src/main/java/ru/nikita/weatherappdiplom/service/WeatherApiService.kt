package ru.nikita.weatherappdiplom.service

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.nikita.weatherappdiplom.dto.WeatherModel

private const val BASE_URL = "https://api.weatherapi.com/v1/"

interface WeatherApiService {

    @GET("forecast.json")
   suspend  fun getWeatherData(
        @Query("key") key: String,
        @Query("q") q: String,
        @Query("days") days: String,
        @Query("aqi") aqi: String,
        @Query("alerts") alerts: String,
        @Query("lang") lang: String,
    ): Response<WeatherModel>
}


val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object WeatherApi {
    val retrofitService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}