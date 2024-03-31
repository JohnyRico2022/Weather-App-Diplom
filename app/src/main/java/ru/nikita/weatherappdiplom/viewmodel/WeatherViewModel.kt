package ru.nikita.weatherappdiplom.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ru.nikita.weatherappdiplom.BuildConfig
import ru.nikita.weatherappdiplom.dto.Hour
import ru.nikita.weatherappdiplom.dto.WeatherModel
import ru.nikita.weatherappdiplom.model.WeatherState
import ru.nikita.weatherappdiplom.service.WeatherApiService
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val api: WeatherApiService
) : ViewModel() {

    private val _data = MutableLiveData<WeatherModel?>()
    val data: LiveData<WeatherModel?>
        get() = _data

    val dataHour = MutableLiveData<List<Hour>>()
    val stateData = MutableLiveData<WeatherState>()


    private val apiKey = BuildConfig.MY_API_KEY
    fun getWeather(city: String, language: String) = viewModelScope.launch {
        stateData.value = WeatherState(loading = true)
        try {
            val response =
                api.getWeatherData(apiKey, city, "3", "no", "no", language)

            if (response.isSuccessful) {
                val weather = response.body()
                _data.value = weather

                stateData.setValue(WeatherState(success = true))

            } else {
                stateData.setValue(WeatherState(error = true))
            }

        } catch (e: Exception) {
            stateData.setValue(WeatherState(internetError = true))
        }
    }
}