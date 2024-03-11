package ru.nikita.weatherappdiplom.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nikita.weatherappdiplom.R
import ru.nikita.weatherappdiplom.databinding.FragmentDayBinding
import ru.nikita.weatherappdiplom.dialogManager.InfoDialog
import ru.nikita.weatherappdiplom.viewmodel.WeatherViewModel


class DayFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDayBinding.inflate(inflater, container, false)

        binding.searchImage.setOnClickListener {
            val textCity = binding.searchCity.text.trim().toString()

            CoroutineScope(Dispatchers.Main).launch {
                viewModel.getWeather(textCity)
            }

            binding.searchCity.setText("")
        }


        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getWeather("London")
            Log.d("MyLog", "Запрос погоды")
        }


        viewModel.data.observe(viewLifecycleOwner) {
            Log.d("MyLog", "day fragment: ${it.current.condition.icon}")
            binding.cityName.text = it.location.name
            binding.currentTemp.text = "${it.current.temp_c} °C"
            binding.condition.text = it.current.condition.text
            Glide.with(binding.imageWeather)
                .load("https:" + it.current.condition.icon)
                .timeout(10_000)
                .into(binding.imageWeather)
        }


        binding.info.setOnClickListener {
            InfoDialog().mainCardInfoDialog(requireContext())
        }

        binding.mainCard.setOnClickListener {
            findNavController().navigate(R.id.action_dayFragment_to_fullCurrentWeatherFragment)
        }


        return binding.root
    }
}