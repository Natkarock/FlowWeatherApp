package com.natkarock.get_cities.useCase

import android.util.Log
import com.natkarock.core.useCase.UseCase
import com.natkarock.core_network.network.data.ApiResult
import com.natkarock.flowweatherapp.ui.main.CitiesRepository
import com.natkarock.get_cities.data.CitiesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class GetCities @Inject constructor(private val repository: CitiesRepository) :
    UseCase<String, ApiResult<CitiesResponse>> {
    private val citiesStateFlow = MutableSharedFlow<String>()

    override suspend fun setup(
        loadingCallback: () -> Unit,
        successCallback: (response: ApiResult<CitiesResponse>) -> Unit
    ) {
        citiesStateFlow.debounce(300).onEach {
            loadingCallback.invoke()
        }.map { repository.getCities(it) }.flowOn(Dispatchers.Main)
            .catch {

            }.collect {
                successCallback.invoke(it)
            }
    }

    override suspend fun action(data: String) {
        citiesStateFlow.emit(data)
    }

}