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
import ru.nikita.weatherappdiplom.databinding.FragmentAstroBinding
import ru.nikita.weatherappdiplom.utils.KEY_AUTH
import ru.nikita.weatherappdiplom.utils.KEY_AUTH_SIGNIN
import ru.nikita.weatherappdiplom.utils.KEY_AUTH_SIGNUP
import ru.nikita.weatherappdiplom.utils.KEY_DATA
import ru.nikita.weatherappdiplom.utils.KEY_DATA_CITY
import ru.nikita.weatherappdiplom.utils.KEY_DATA_LANGUAGE
import ru.nikita.weatherappdiplom.utils.MoonPhases
import ru.nikita.weatherappdiplom.viewmodel.WeatherViewModel


class AstroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: WeatherViewModel by viewModels()
        val binding = FragmentAstroBinding.inflate(inflater, container, false)

        val pref = this.requireActivity()
            .getSharedPreferences(KEY_DATA, Context.MODE_PRIVATE)
        val city = pref.getString(KEY_DATA_CITY, "Moscow").toString()
        val language = pref.getString(KEY_DATA_LANGUAGE, "en").toString()

        val preferences = this.requireActivity()
            .getSharedPreferences(KEY_AUTH, Context.MODE_PRIVATE)

        val userSignUp = preferences.getString(KEY_AUTH_SIGNIN, "signIn").toString()
        val userSignIn = preferences.getString(KEY_AUTH_SIGNUP, "signUp").toString()
        updateUI(binding, userSignIn, userSignUp)


        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getWeather(city, language)
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