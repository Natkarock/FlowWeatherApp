package com.natkarock.flowweatherapp.model

data class Weather(
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double,
    val city: String
)