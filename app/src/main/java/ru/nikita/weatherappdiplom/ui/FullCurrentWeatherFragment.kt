package ru.nikita.weatherappdiplom.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nikita.weatherappdiplom.R
import ru.nikita.weatherappdiplom.databinding.FragmentFullCurrentWeatherBinding
import ru.nikita.weatherappdiplom.utils.KEY_DATA
import ru.nikita.weatherappdiplom.utils.KEY_DATA_CITY
import ru.nikita.weatherappdiplom.utils.KEY_DATA_LANGUAGE
import ru.nikita.weatherappdiplom.viewmodel.WeatherViewModel

class FullCurrentWeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFullCurrentWeatherBinding.inflate(inflater, container, false)

        val pref = this.requireActivity()
            .getSharedPreferences(KEY_DATA, Context.MODE_PRIVATE)

        val city = pref.getString(KEY_DATA_CITY, "Moscow").toString()
        val language = pref.getString(KEY_DATA_LANGUAGE, "en").toString()

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getWeather(city, language)
        }


        viewModel.data.observe(viewLifecycleOwner) {
            val wind = it.forecast.forecastday[0].day.maxwind_kph
            val visibility = it.forecast.forecastday[0].day.avgvis_km
            val precipitation = it.forecast.forecastday[0].day.totalprecip_mm

            with(binding){
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

        binding.backToFragmentDay.setOnClickListener {
            findNavController().popBackStack(R.id.dayFragment, false)
        }

        return binding.root
    }
}