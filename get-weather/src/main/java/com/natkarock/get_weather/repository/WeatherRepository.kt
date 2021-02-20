package com.natkarock.flowweatherapp.ui.main

import com.natkarock.core_network.di.network.BaseApiCall

import com.natkarock.core_network.network.data.ApiResult
import com.natkarock.get_weather.api.WeatherApi
import com.natkarock.get_weather.data.WeatherResponse
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

interface WeatherRepository {
    suspend fun getWeather(name: String): ApiResult<WeatherResponse>
}


@ViewModelScoped
class WeatherRepositoryImpl @Inject constructor(private val weatherApi: WeatherApi) :
    WeatherRepository {
    override suspend fun getWeather(name: String): ApiResult<WeatherResponse> {
        return   BaseApiCall.safeApiCall(weatherApi.getWeather(name))
    }

}