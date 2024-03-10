package ru.nikita.weatherappdiplom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.nikita.weatherappdiplom.dto.WeatherModel
import ru.nikita.weatherappdiplom.service.WeatherApi
import ru.nikita.weatherappdiplom.service.WeatherApiService

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    //  val dataDay: MutableLiveData<Day> = apiService.dataDay
    val data = MutableLiveData<WeatherModel>()


    suspend fun getWeather() {
        val response = WeatherApi.retrofitService.getWeatherData(
            "51e4803842ee448fa0795006222906",
            "London",
            "3",
            "no",
            "no",
            "eng"
        )

        data.value = response
    }


}