package ru.nikita.weatherappdiplom.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import ru.nikita.weatherappdiplom.databinding.FragmentAstroBinding
import ru.nikita.weatherappdiplom.utils.KEY_AUTH
import ru.nikita.weatherappdiplom.utils.KEY_AUTH_SIGNIN
import ru.nikita.weatherappdiplom.utils.KEY_AUTH_SIGNUP
import ru.nikita.weatherappdiplom.utils.KEY_SETTINGS
import ru.nikita.weatherappdiplom.utils.KEY_SETTINGS_LANGUAGE
import ru.nikita.weatherappdiplom.utils.KEY_WEATHER
import ru.nikita.weatherappdiplom.utils.KEY_WEATHER_CITY
import ru.nikita.weatherappdiplom.utils.MoonPhases
import ru.nikita.weatherappdiplom.viewmodel.WeatherViewModel
import javax.inject.Inject

@AndroidEntryPoint
class AstroFragment : Fragment() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val viewModel: WeatherViewModel by viewModels()

    @Inject
    lateinit var moonPhases: MoonPhases

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAstroBinding.inflate(inflater, container, false)

        val prefSettings = this.requireActivity()
            .getSharedPreferences(KEY_SETTINGS, Context.MODE_PRIVATE)
        val language = prefSettings.getString(KEY_SETTINGS_LANGUAGE, "en").toString()

        val prefWeather = this.requireActivity()
            .getSharedPreferences(KEY_WEATHER, Context.MODE_PRIVATE)
        val city = prefWeather.getString(KEY_WEATHER_CITY, "Moscow").toString()

        val preferences = this.requireActivity()
            .getSharedPreferences(KEY_AUTH, Context.MODE_PRIVATE)
        val userSignUp = preferences.getString(KEY_AUTH_SIGNIN, "signUp").toString()
        val userSignIn = preferences.getString(KEY_AUTH_SIGNUP, "signIn").toString()

        updateUI(binding, userSignIn, userSignUp)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getWeather(city, language)
        }

        viewModel.data.observe(viewLifecycleOwner) {

            val moonPhaseString = it!!.forecast.forecastday[0].astro.moon_phase

            // Вспомогательный блок кода для перевода фазы луны при смене языка.
            // Прямой подстановкой работает не корректно!
            binding.moonHelper.setText(moonPhases.changeMoonPhases(moonPhaseString))
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
                moonPhaseImage.setImageResource(moonPhases.setMoonImage(moonPhaseString))
            }
        }

        viewModel.stateData.observe(viewLifecycleOwner) { state ->
            with(binding) {
                progressBottom.isVisible = state.loading
                progressTop.isVisible = state.loading

                infoFrameBottom.isVisible = state.success
                infoFrameTop.isVisible = state.success

                errorFrameBottom.isVisible = state.error
                errorFrameTop.isVisible = state.error

                errorInternetFrameBottom.isVisible = state.internetError
                errorInternetFrameTop.isVisible = state.internetError
            }
        }

        return binding.root
    }

    private fun updateUI(binding: FragmentAstroBinding, signInPref: String, signUpPref: String) {
        if (signInPref == signUpPref) {
            binding.frameLayoutBottom.visibility = View.VISIBLE
            binding.frameLayoutTop.visibility = View.VISIBLE
            binding.noAccessCardAstroFragment.visibility = View.GONE
        } else {
            binding.frameLayoutBottom.visibility = View.GONE
            binding.frameLayoutTop.visibility = View.GONE
            binding.noAccessCardAstroFragment.visibility = View.VISIBLE
        }
    }
}