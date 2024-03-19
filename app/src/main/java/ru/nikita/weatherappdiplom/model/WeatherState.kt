package ru.nikita.weatherappdiplom.model


data class WeatherState(
    val loading: Boolean = false,
    val internetError: Boolean = false,
    val error: Boolean = false,
    val success: Boolean = false
)
