package ru.nikita.weatherappdiplom.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nikita.weatherappdiplom.R
import ru.nikita.weatherappdiplom.databinding.FragmentFullCurrentWeatherBinding
import ru.nikita.weatherappdiplom.utils.KEY_SETTINGS
import ru.nikita.weatherappdiplom.utils.KEY_SETTINGS_LANGUAGE
import ru.nikita.weatherappdiplom.utils.KEY_WEATHER
import ru.nikita.weatherappdiplom.utils.KEY_WEATHER_CITY
import ru.nikita.weatherappdiplom.viewmodel.WeatherViewModel

class FullCurrentWeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFullCurrentWeatherBinding.inflate(inflater, container, false)

        val prefSettings = this.requireActivity()
            .getSharedPreferences(KEY_SETTINGS, Context.MODE_PRIVATE)
        val language = prefSettings.getString(KEY_SETTINGS_LANGUAGE, "en").toString()

        val prefWeather = this.requireActivity()
            .getSharedPreferences(KEY_WEATHER, Context.MODE_PRIVATE)
        val city = prefWeather.getString(KEY_WEATHER_CITY, "Moscow").toString()

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getWeather(city, language)
        }

        viewModel.data.observe(viewLifecycleOwner) {
            val wind = it!!.forecast.forecastday[0].day.maxwind_kph
            val visibility = it.forecast.forecastday[0].day.avgvis_km
            val precipitation = it.forecast.forecastday[0].day.totalprecip_mm

            with(binding) {
                minTempValue.text = "${it.forecast.forecastday[0].day.mintemp_c} °C"
                maxTempValue.text = "${it.forecast.forecastday[0].day.maxtemp_c} °C"
                averageTempValue.text = "${it.forecast.forecastday[0].day.avgtemp_c} °C"
                windValue.text = getString(R.string.km_h, wind.toString())
                averageVisibilityValue.text = getString(R.string.km_h, visibility.toString())
                precipitationValue.text = getString(R.string.mm, precipitation.toString())
                humidityValue.text = "${it.forecast.forecastday[0].day.avghumidity} %"
                rainChanceValue.text = "${it.forecast.forecastday[0].day.daily_chance_of_rain} %"
                snowChanceValue.text = "${it.forecast.forecastday[0].day.daily_chance_of_snow} %"
            }
        }

        viewModel.stateData.observe(viewLifecycleOwner) { state ->
            binding.progressFullCurrentWeatherFragment.isVisible = state.loading
            binding.group.isVisible = state.success
            binding.errorFrame.isVisible = state.error
            binding.internetErrorFrame.isVisible = state.internetError
        }

        binding.backToFragmentDay.setOnClickListener {
            findNavController().popBackStack(R.id.dayFragment, false)
        }

        return binding.root
    }
}