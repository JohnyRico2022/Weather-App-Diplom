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
import ru.nikita.weatherappdiplom.adapter.DaysAdapter
import ru.nikita.weatherappdiplom.adapter.HoursAdapter
import ru.nikita.weatherappdiplom.adapter.OnInteractionListener
import ru.nikita.weatherappdiplom.databinding.FragmentWeekBinding
import ru.nikita.weatherappdiplom.dto.Forecastday
import ru.nikita.weatherappdiplom.utils.KEY_AUTH
import ru.nikita.weatherappdiplom.utils.KEY_AUTH_SIGNIN
import ru.nikita.weatherappdiplom.utils.KEY_AUTH_SIGNUP
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

        val prefAuth = this.requireActivity()
            .getSharedPreferences(KEY_AUTH, Context.MODE_PRIVATE)
        val userSignUp = prefAuth.getString(KEY_AUTH_SIGNIN, "signIn").toString()
        val userSignIn = prefAuth.getString(KEY_AUTH_SIGNUP, "signUp").toString()
        updateUI(binding, userSignIn, userSignUp)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getWeather(city, language)
        }


        val adapterDays = DaysAdapter(object : OnInteractionListener{
            override fun onItemClicked(itemDay: Forecastday) {
                val listHour = itemDay.hour
                viewModel.dataHour.value = listHour

                binding.textHoursRecycler.visibility = View.GONE
                binding.hoursRecycler.visibility = View.VISIBLE
            }
        })
        binding.daysRecycler.adapter = adapterDays

        viewModel.data.observe(viewLifecycleOwner){
            adapterDays.submitList(it!!.forecast.forecastday)
        }



        val adapterHours = HoursAdapter()
        binding.hoursRecycler.adapter = adapterHours

        viewModel.dataHour.observe(viewLifecycleOwner){
            adapterHours.submitList(it)
        }

        return binding.root
    }

    private fun updateUI(binding: FragmentWeekBinding, userSignIn: String, userSignUp: String) {
        if (userSignIn == userSignUp) {
            binding.frameLayoutDays.visibility = View.VISIBLE
            binding.frameLayoutHours.visibility = View.VISIBLE
            binding.noAccessCardWeekFragment.visibility = View.GONE
        } else {
            binding.frameLayoutDays.visibility = View.GONE
            binding.frameLayoutHours.visibility = View.GONE
            binding.noAccessCardWeekFragment.visibility = View.VISIBLE
        }
    }
}