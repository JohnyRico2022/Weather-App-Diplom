package ru.nikita.weatherappdiplom.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nikita.weatherappdiplom.databinding.FragmentWeekBinding
import ru.nikita.weatherappdiplom.viewmodel.WeatherViewModel


class WeekFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: WeatherViewModel by viewModels()
        val binding = FragmentWeekBinding.inflate(inflater, container, false)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getWeather("London")
        }

        viewModel.data.observe(viewLifecycleOwner) {
            binding.textView.text = it.location.name

            Log.d("MyLog", "from week fragment${it.location.name}")
        }
        binding.buttonX.setOnClickListener {
            Log.d("MyLog", "week fragment button clicked ")




        }




        return binding.root
    }
}