package ru.nikita.weatherappdiplom.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nikita.weatherappdiplom.databinding.FragmentWeekBinding
import ru.nikita.weatherappdiplom.utils.KEY_SETTINGS
import ru.nikita.weatherappdiplom.utils.KEY_SETTINGS_LANGUAGE
import ru.nikita.weatherappdiplom.utils.KEY_WEATHER
import ru.nikita.weatherappdiplom.utils.KEY_WEATHER_CITY
import ru.nikita.weatherappdiplom.viewmodel.WeatherViewModel


class WeekFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: WeatherViewModel by viewModels()
        val binding = FragmentWeekBinding.inflate(inflater, container, false)

        val prefSettings = this.requireActivity()
            .getSharedPreferences(KEY_SETTINGS, Context.MODE_PRIVATE)
        val language = prefSettings.getString(KEY_SETTINGS_LANGUAGE, "en").toString()

        val prefWeather = this.requireActivity()
            .getSharedPreferences(KEY_WEATHER, Context.MODE_PRIVATE)
        val city = prefWeather.getString(KEY_WEATHER_CITY, "Moscow").toString()

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getWeather(city, language)
        }





        return binding.root
    }
}