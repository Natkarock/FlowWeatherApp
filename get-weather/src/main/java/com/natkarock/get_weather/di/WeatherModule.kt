package com.natkarock.get_weather.di

import com.natkarock.flowweatherapp.ui.main.WeatherRepository
import com.natkarock.flowweatherapp.ui.main.WeatherRepositoryImpl
import com.natkarock.get_weather.api.WeatherApi
import com.natkarock.get_weather.useCase.GetWeather
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class WeatherModule {

    @Provides
    @ViewModelScoped
    fun weatherRepository(weatherApi: WeatherApi): WeatherRepository =
        WeatherRepositoryImpl(weatherApi)
}