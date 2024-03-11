package ru.nikita.weatherappdiplom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.nikita.weatherappdiplom.dto.WeatherModel
import ru.nikita.weatherappdiplom.service.WeatherApi

class WeatherViewModel(application: Application) : AndroidViewModel(application) {


    val data = MutableLiveData<WeatherModel>()


    suspend fun getWeather(city: String, language: String): MutableLiveData<WeatherModel> {
        val response = WeatherApi.retrofitService.getWeatherData(
            "51e4803842ee448fa0795006222906",
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