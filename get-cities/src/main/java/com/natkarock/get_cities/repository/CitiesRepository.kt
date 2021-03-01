package com.natkarock.flowweatherapp.ui.main

import com.natkarock.core_network.network.BaseApiCall
import com.natkarock.get_cities.data.CitiesResponse
import com.natkarock.core_network.network.data.ApiResult
import com.natkarock.get_cities.api.CitiesApi
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject




interface CitiesRepository{
    suspend fun getCities(name: String): ApiResult<CitiesResponse>
}

@ViewModelScoped
class CitiesRepositoryImpl @Inject constructor(private val citiesApi: CitiesApi): CitiesRepository {

    override suspend fun getCities(name: String): ApiResult<CitiesResponse> {
       return   BaseApiCall.safeApiCall(citiesApi.getCities(name))
    }


}