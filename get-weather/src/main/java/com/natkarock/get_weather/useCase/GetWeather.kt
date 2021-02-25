package com.natkarock.get_weather.useCase

import com.natkarock.core.useCase.UseCase
import com.natkarock.core_network.network.data.ApiResult
import com.natkarock.flowweatherapp.ui.main.WeatherRepository
import com.natkarock.get_weather.data.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetWeather @Inject constructor(private val repository: WeatherRepository) :
    UseCase<String, ApiResult<WeatherResponse>> {

    private val weatherFlow = MutableSharedFlow<String>()


    override suspend fun action(data: String) {
        weatherFlow.emit(data)
    }

    override suspend fun setup(
        loadingCallback: () -> Unit,
        successCallback: (response: ApiResult<WeatherResponse>) -> Unit
    ) {
        weatherFlow.debounce(300).onEach {
            loadingCallback.invoke()
        }.map { repository.getWeather(it) }
            .flowOn(Dispatchers.Main)
            .catch { }
            .collect {
                successCallback.invoke(it)
            }
    }


}