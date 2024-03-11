package ru.nikita.weatherappdiplom.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nikita.weatherappdiplom.databinding.FragmentAstroBinding
import ru.nikita.weatherappdiplom.utils.MoonPhases
import ru.nikita.weatherappdiplom.viewmodel.WeatherViewModel


class AstroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: WeatherViewModel by viewModels()
        val binding = FragmentAstroBinding.inflate(inflater, container, false)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getWeather("London")
        }



        viewModel.data.observe(viewLifecycleOwner) {

            val moonPhaseString = it.forecast.forecastday[0].astro.moon_phase

            // Вспомогательный блок кода для перевода фазы луны при смене языка.
            // Прямой подстановкой работает не корректно!
            binding.moonHelper.setText(MoonPhases().changeMoonPhases(moonPhaseString))
            val helperMoonPhase = binding.moonHelper.text
            // Конец вспомогательного блока


            with(binding) {
                sunRiseValue.text = it.forecast.forecastday[0].astro.sunrise
                sunSetValue.text = it.forecast.forecastday[0].astro.sunset
                moonRiseValue.text = it.forecast.forecastday[0].astro.moonrise
                moonSetValue.text = it.forecast.forecastday[0].astro.moonset
                moonPhaseValue.text = helperMoonPhase
                moonIlluminationValue.text =
                    "${it.forecast.forecastday[0].astro.moon_illumination} %"
                moonPhaseImage.setImageResource(MoonPhases().setMoonImage(moonPhaseString))
            }
        }

        return binding.root
    }
}