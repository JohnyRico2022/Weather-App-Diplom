package ru.nikita.weatherappdiplom.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import ru.nikita.weatherappdiplom.BuildConfig
import ru.nikita.weatherappdiplom.R
import ru.nikita.weatherappdiplom.databinding.FragmentSettingsBinding
import ru.nikita.weatherappdiplom.utils.KEY_SETTINGS
import ru.nikita.weatherappdiplom.utils.KEY_SETTINGS_LANGUAGE
import ru.nikita.weatherappdiplom.utils.KEY_SETTINGS_RADIO_BUTTON
import ru.nikita.weatherappdiplom.utils.KEY_SETTINGS_RATING

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       val binding = FragmentSettingsBinding.inflate(inflater, container, false)

        val pref = this.requireActivity()
            .getSharedPreferences(KEY_SETTINGS, Context.MODE_PRIVATE)

        binding.versionInfoValue.text = BuildConfig.VERSION_NAME

        val getRating = pref.getFloat(KEY_SETTINGS_RATING, 3.5f)
        val buttonChecked = pref.getInt(KEY_SETTINGS_RADIO_BUTTON, R.id.en_button)

        binding.backToFragmentUser.setOnClickListener {
            findNavController().popBackStack(R.id.userInfoFragment, false)
        }


        binding.radioGroup.check(buttonChecked)

        binding.ruButton.setOnClickListener {
            val lang = "ru"
            pref.edit()
                .putString(KEY_SETTINGS_LANGUAGE, lang)
                .putInt(KEY_SETTINGS_RADIO_BUTTON, R.id.ru_button)
                .apply()
        }
        binding.enButton.setOnClickListener {
            val lang = "en"
            pref.edit()
                .putString(KEY_SETTINGS_LANGUAGE, lang)
                .putInt(KEY_SETTINGS_RADIO_BUTTON, R.id.en_button)
                .apply()
        }
        binding.itButton.setOnClickListener {
            val lang = "it"
            pref.edit()
                .putString(KEY_SETTINGS_LANGUAGE, lang)
                .putInt(KEY_SETTINGS_RADIO_BUTTON, R.id.it_button)
                .apply()
        }


        binding.ratingBar.rating = getRating

        binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->

            binding.ratingButton.setOnClickListener {
                pref.edit()
                    .putFloat(KEY_SETTINGS_RATING, rating)
                    .apply()


                Toast.makeText(
                    requireContext(), getString(R.string.rates_this_app, rating.toString()),
                    Toast.LENGTH_SHORT
                ).show()

            }
        }


        return binding.root
    }
}