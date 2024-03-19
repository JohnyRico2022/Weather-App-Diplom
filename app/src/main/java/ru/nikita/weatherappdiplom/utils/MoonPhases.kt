package ru.nikita.weatherappdiplom.utils

import ru.nikita.weatherappdiplom.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoonPhases @Inject constructor() {

    fun changeMoonPhases(phase: String): Int {
        return when (phase) {

            "New Moon" -> R.string.new_moon

            "Waxing Crescent" -> R.string.waxing_crescent

            "First Quarter" -> R.string.first_quarter

            "Waxing Gibbous" -> R.string.waxing_gibbous

            "Full Moon" -> R.string.full_moon

            "Waning Gibbous" -> R.string.waning_gibbous

            "Last Quarter" -> R.string.last_quarter

            "Waning Crescent" -> R.string.waning_crescent

            else -> R.string.error_moon_phase
        }
    }

    fun setMoonImage(phase: String): Int {
        return when (phase) {

            "New Moon" -> R.drawable.new_moon

            "Waxing Crescent" -> R.drawable.waxing_crescent

            "First Quarter" -> R.drawable.first_quarter

            "Waxing Gibbous" -> R.drawable.waxing_gibbous

            "Full Moon" -> R.drawable.full_moon

            "Waning Gibbous" -> R.drawable.waning_gibbous

            "Last Quarter" -> R.drawable.last_quarter

            "Waning Crescent" -> R.drawable.waning_crescent

            else -> R.drawable.ic_error_24
        }
    }
}