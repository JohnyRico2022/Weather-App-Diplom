package ru.nikita.weatherappdiplom.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import ru.nikita.weatherappdiplom.R
import ru.nikita.weatherappdiplom.databinding.FragmentUserInfoBinding
import ru.nikita.weatherappdiplom.dialogManager.InfoDialog
import ru.nikita.weatherappdiplom.utils.KEY_AUTH
import ru.nikita.weatherappdiplom.utils.KEY_AUTH_SIGNIN
import ru.nikita.weatherappdiplom.utils.KEY_AUTH_SIGNUP
import ru.nikita.weatherappdiplom.utils.KEY_LOGIN_NAME
import javax.inject.Inject

@AndroidEntryPoint
class UserInfoFragment : Fragment() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentUserInfoBinding.inflate(inflater, container, false)

        val preferences = this.requireActivity()
            .getSharedPreferences(KEY_AUTH, Context.MODE_PRIVATE)

        val userSignUp = preferences.getString(KEY_AUTH_SIGNIN, "signIn").toString()
        val userSignIn = preferences.getString(KEY_AUTH_SIGNUP, "signUp").toString()
        val userLogin = preferences.getString(KEY_LOGIN_NAME, "Login")
        binding.loginTextMainCard.text = userLogin
        updateMainCard(binding, userSignIn, userSignUp)

        binding.signUpMainCard.setOnClickListener {
            binding.signUpCard.visibility = View.VISIBLE
            binding.signInCard.visibility = View.GONE
        }

        binding.buttonSignUp.setOnClickListener {
            val login = binding.loginSignUp.text.trim().toString()
            val email = binding.emailSignUp.text.trim().toString()
            val pass = binding.passSignUp.text.trim().toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && login.isNotEmpty()) {

                firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {

                            successfulSignUp(binding)

                            val userUid = firebaseAuth.uid.toString()
                            preferences.edit()
                                .putString(KEY_AUTH_SIGNUP, userUid)
                                .putString(KEY_AUTH_SIGNIN, userUid)
                                .putString(KEY_LOGIN_NAME, login)
                                .apply()

                            updateMainCard(binding, userUid, userUid)
                            binding.loginTextMainCard.text = login
                        } else {
                            InfoDialog().registrationInfoDialog(requireContext())
                        }
                    }

            } else {
                toastAllFields()
            }
        }

        binding.signInMainCard.setOnClickListener {
            binding.signInCard.visibility = View.VISIBLE
            binding.signUpCard.visibility = View.GONE
        }


        binding.buttonSignIn.setOnClickListener {
            val email = binding.mailSignIn.text.trim().toString()
            val pass = binding.passSignIn.text.trim().toString()
            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {

                            successfulSignIn(binding)

                            val user = firebaseAuth.uid
                            preferences.edit()
                                .putString(KEY_AUTH_SIGNIN, user)
                                .apply()

                            val userLogin2 = preferences.getString(KEY_LOGIN_NAME, "Login")
                            binding.loginTextMainCard.text = userLogin2
                            val userSignUp2 =
                                preferences.getString(KEY_AUTH_SIGNIN, "signIn").toString()
                            val userSignIn2 =
                                preferences.getString(KEY_AUTH_SIGNUP, "signUp").toString()
                            updateMainCard(binding, userSignIn2, userSignUp2)


                        } else {
                            InfoDialog().registrationInfoDialog(requireContext())
                        }
                    }
            } else {
                toastAllFields()
            }
        }

        binding.signOutMainCard.setOnClickListener {

            preferences.edit()
                .putString(KEY_AUTH_SIGNIN, "signIn")
                .apply()

            val userSignIn3 = preferences.getString(KEY_AUTH_SIGNIN, "signIn").toString()
            val userSignUp3 = preferences.getString(KEY_AUTH_SIGNUP, "signUp").toString()
            updateMainCard(binding, userSignIn3, userSignUp3)
            Toast.makeText(requireContext(), R.string.sign_out_successful, Toast.LENGTH_SHORT)
                .show()
        }

        binding.settings.setOnClickListener {
            findNavController().navigate(R.id.action_userInfoFragment_to_settingsFragment)
        }


        return binding.root
    }

    private fun updateMainCard(
        binding: FragmentUserInfoBinding,
        signInPref: String,
        signUpPref: String
    ) {

        if (signInPref == signUpPref) {
            binding.loginTextMainCard.visibility = View.VISIBLE
            binding.signOutMainCard.visibility = View.VISIBLE

            binding.withAsMainCard.visibility = View.GONE
            binding.signUpMainCard.visibility = View.GONE
            binding.signInMainCard.visibility = View.GONE
        } else {
            binding.loginTextMainCard.visibility = View.GONE
            binding.signOutMainCard.visibility = View.GONE

            binding.withAsMainCard.visibility = View.VISIBLE
            binding.signUpMainCard.visibility = View.VISIBLE
            binding.signInMainCard.visibility = View.VISIBLE
        }
    }

    private fun successfulSignUp(binding: FragmentUserInfoBinding) {
        Toast.makeText(requireContext(), R.string.sign_up_successful, Toast.LENGTH_SHORT).show()
        binding.loginSignUp.setText("")
        binding.emailSignUp.setText("")
        binding.passSignUp.setText("")
        binding.signUpCard.visibility = View.GONE

    }

    private fun successfulSignIn(binding: FragmentUserInfoBinding) {
        Toast.makeText(requireContext(), R.string.sign_in_successful, Toast.LENGTH_SHORT).show()
        binding.mailSignIn.setText("")
        binding.passSignIn.setText("")
        binding.signInCard.visibility = View.GONE
    }

    private fun toastAllFields() {
        Toast.makeText(requireContext(), R.string.all_fields_must_be_filled_in, Toast.LENGTH_SHORT)
            .show()
    }
}

