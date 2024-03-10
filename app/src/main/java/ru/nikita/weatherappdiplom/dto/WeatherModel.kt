package ru.nikita.weatherappdiplom.dto

data class WeatherModel(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)