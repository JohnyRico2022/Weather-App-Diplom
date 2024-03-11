package ru.nikita.weatherappdiplom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.nikita.weatherappdiplom.BuildConfig
import ru.nikita.weatherappdiplom.dto.WeatherModel
import ru.nikita.weatherappdiplom.service.WeatherApi

class WeatherViewModel(application: Application) : AndroidViewModel(application) {


    val data = MutableLiveData<WeatherModel>()

    private val apiKey = BuildConfig.MY_API_KEY

    suspend fun getWeather(city: String, language: String): MutableLiveData<WeatherModel> {
        val response = WeatherApi.retrofitService.getWeatherData(
            apiKey,
            city,
            "3",
            "no",
            "no",
            language
        )

        data.value = response
        return data
    }
}