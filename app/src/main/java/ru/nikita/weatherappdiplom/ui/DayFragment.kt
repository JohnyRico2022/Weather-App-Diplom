package ru.nikita.weatherappdiplom.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.location.Priority
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nikita.weatherappdiplom.BuildConfig
import ru.nikita.weatherappdiplom.R
import ru.nikita.weatherappdiplom.databinding.FragmentDayBinding
import ru.nikita.weatherappdiplom.dialogManager.AccessDialog
import ru.nikita.weatherappdiplom.dialogManager.DialogClickListener
import ru.nikita.weatherappdiplom.dialogManager.InfoDialog
import ru.nikita.weatherappdiplom.dialogManager.LocationDialog
import ru.nikita.weatherappdiplom.utils.AndroidUtils
import ru.nikita.weatherappdiplom.utils.KEY_DATA
import ru.nikita.weatherappdiplom.utils.KEY_DATA_CITY
import ru.nikita.weatherappdiplom.utils.KEY_DATA_LANGUAGE
import ru.nikita.weatherappdiplom.viewmodel.WeatherViewModel
import ru.nikita.weatherappdiplom.utils.isPermissionGranted


class DayFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var launcher: ActivityResultLauncher<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDayBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        checkPermission()
        val pref = this.requireActivity()
            .getSharedPreferences(KEY_DATA, Context.MODE_PRIVATE)

        val city = pref.getString(KEY_DATA_CITY, "Moscow").toString()
        val language = pref.getString(KEY_DATA_LANGUAGE, "en").toString()

       /* val key = BuildConfig.MY_API_KEY
        Log.d("MyLog", "Api key: $key")*/

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getWeather(city, language)
        }


        binding.searchImage.setOnClickListener {
            val textCity = binding.searchCity.text.trim().toString()

            if (textCity.length > 2) {
                pref.edit()
                    .putString(KEY_DATA_CITY, textCity)
                    .apply()
                AndroidUtils.hideKeyboard(requireView())
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.getWeather(textCity, language)
                }
                binding.searchCity.setText("")
            } else {
                InfoDialog().mainCardInfoDialog(requireContext())
            }
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


        binding.location.setOnClickListener {
            getLocation(viewModel)
        }

        binding.info.setOnClickListener {
            InfoDialog().mainCardInfoDialog(requireContext())
        }


        binding.mainCard.setOnClickListener {
            findNavController().navigate(R.id.action_dayFragment_to_fullCurrentWeatherFragment)
        }


        return binding.root
    }

    private fun isLocationEnabled(): Boolean {   //проверка, включен ли GPS
        val locationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun getLocation(viewModel: WeatherViewModel) {

        if (!isLocationEnabled()) {
            LocationDialog().settingsLocation(requireContext(), object : DialogClickListener {
                override fun onClick() {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            })
            return
        } else {

            val pref = this.requireActivity()
                .getSharedPreferences(KEY_DATA, Context.MODE_PRIVATE)
            val language = pref.getString(KEY_DATA_LANGUAGE, "en").toString()

            val token = CancellationTokenSource()

            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                AccessDialog().accessLocationLimited(requireContext(), object :
                    DialogClickListener {
                    override fun onClick() {
                        startActivity(Intent(Settings.ACTION_APPLICATION_SETTINGS))
                    }
                })
                return
            }
            fusedLocationClient
                .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, token.token)
                .addOnCompleteListener {
                    val currentCity = "${it.result.latitude},${it.result.longitude}"

                    CoroutineScope(Dispatchers.Main).launch {
                        viewModel.getWeather(currentCity, language)
                    }

                    pref.edit()
                        .putString(KEY_DATA_CITY, currentCity)
                        .apply()
                }
        }
    }

    private fun permissionListener() {
        launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        }
    }

    private fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}