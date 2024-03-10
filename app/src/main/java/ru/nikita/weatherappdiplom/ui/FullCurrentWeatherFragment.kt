package ru.nikita.weatherappdiplom.ui

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
import ru.nikita.weatherappdiplom.viewmodel.WeatherViewModel

class FullCurrentWeatherFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFullCurrentWeatherBinding.inflate(inflater, container, false)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getWeather()
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