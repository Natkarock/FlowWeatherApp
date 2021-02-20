package com.natkarock.get_cities.di

import com.natkarock.get_cities.api.CitiesApi
import com.natkarock.flowweatherapp.ui.main.CitiesRepository
import com.natkarock.flowweatherapp.ui.main.CitiesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class CitiesModule {

    @Provides
    @ViewModelScoped
    fun citiesRepository(citiesApi: CitiesApi): CitiesRepository = CitiesRepositoryImpl(citiesApi)
}