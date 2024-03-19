package ru.nikita.weatherappdiplom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.nikita.weatherappdiplom.BuildConfig
import ru.nikita.weatherappdiplom.dto.Hour
import ru.nikita.weatherappdiplom.dto.WeatherModel
import ru.nikita.weatherappdiplom.model.WeatherState
import ru.nikita.weatherappdiplom.service.WeatherApi

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val _data = MutableLiveData<WeatherModel?>()
    val data: LiveData<WeatherModel?>
        get() = _data

    val dataHour = MutableLiveData<List<Hour>>()
    val stateData = MutableLiveData<WeatherState>()


    private val apiKey = BuildConfig.MY_API_KEY
    fun getWeather(city: String, language: String) = viewModelScope.launch {
        stateData.postValue(WeatherState(loading = true))
        try {
            val response =
                WeatherApi.retrofitService.getWeatherData(apiKey, city, "3", "no", "no", language)

            if (response.isSuccessful) {
                val weather = response.body()
                _data.value = weather

                stateData.postValue(WeatherState(success = true))

            } else {
                stateData.postValue(WeatherState(error = true))
            }

        } catch (e: Exception) {
            stateData.postValue(WeatherState(internetError = true))
        }
    }
}