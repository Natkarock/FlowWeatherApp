package com.natkarock.get_weather.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query(value = "q") name: String,
        @Query(value = "appid") key: String = API_KEY,
        @Query(value = "units") units: String = "metric"
    ): Response<com.natkarock.get_weather.data.WeatherResponse>

    companion object {
        private const val API_KEY = "86f5d5210b2f154859dadca9a0345cd0"
    }
}