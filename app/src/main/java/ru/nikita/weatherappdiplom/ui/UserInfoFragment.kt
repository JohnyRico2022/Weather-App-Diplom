package ru.nikita.weatherappdiplom.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.nikita.weatherappdiplom.R
import ru.nikita.weatherappdiplom.databinding.FragmentUserInfoBinding

class UserInfoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         val binding = FragmentUserInfoBinding.inflate(inflater, container, false)




        binding.settings.setOnClickListener {
            findNavController().navigate(R.id.action_userInfoFragment_to_settingsFragment)
        }


        return binding.root
    }
}