package ru.nikita.weatherappdiplom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.nikita.weatherappdiplom.BuildConfig
import ru.nikita.weatherappdiplom.dto.Hour
import ru.nikita.weatherappdiplom.dto.WeatherModel
import ru.nikita.weatherappdiplom.model.WeatherState
import ru.nikita.weatherappdiplom.service.WeatherApi

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    val data = MutableLiveData<WeatherModel?>()
    val dataHour = MutableLiveData<List<Hour>>()

    val stateData = MutableLiveData<WeatherState>()


    private val apiKey = BuildConfig.MY_API_KEY
    fun getWeather(city: String, language: String) {

        WeatherApi.retrofitService
            .getWeatherData(apiKey, city, "3", "no", "no", language)
            .enqueue(object : Callback<WeatherModel> {
                override fun onResponse(
                    call: Call<WeatherModel>,
                    response: Response<WeatherModel>
                ) {
                    if (response.isSuccessful) {
                        val weather = response.body()
                        data.value = weather

                        stateData.postValue(WeatherState(success = true))

                    } else {
                        stateData.postValue(WeatherState(error = true))
                    }
                }

                override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                    stateData.postValue(WeatherState(internetError = true))
                }
            })
    }
}