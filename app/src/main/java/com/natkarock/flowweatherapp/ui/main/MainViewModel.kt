package com.natkarock.flowweatherapp.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.natkarock.flowweatherapp.model.*
import com.natkarock.core_network.network.data.ApiResult
import com.natkarock.get_cities.data.CitiesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


interface MainViewModel {
    suspend fun getCities(name: String): ApiResult<CitiesResponse>
    suspend fun getWeather(name: String): ApiResult<com.natkarock.get_weather.data.WeatherResponse>
    fun setCitiesSearch(text: String)
    fun setWeatherSearch(position: Int)
}

@HiltViewModel
class MainViewModelImpl   @Inject constructor (
    private val weatherRepository: WeatherRepository,
    private val citiesRepository: CitiesRepository,
    private  val savedStateHandle: SavedStateHandle
) : ViewModel(), MainViewModel, BaseViewModel {


    val weatherModel = MutableLiveData<UIModel<Weather>>(UIModel.Loading(false))
    val citiesModel = MutableLiveData<UIModel<List<City>>>(UIModel.Loading(false))


    private val citiesStateFlow = MutableSharedFlow<String>()
    private val weatherFlow = MutableSharedFlow<String>()

    init {
        initCitiesFlow()
        initWeatherFlow()
    }

    private fun initCitiesFlow() {
        viewModelScope.launch {
            citiesStateFlow.debounce(300).map { getCities(it) }.onEach {
                citiesModel.postValue(UIModel.Loading(true))
            }.flowOn(Dispatchers.Main)
                .catch {
                    Log.e(LOG, it.toString())
                }.collect {
                    val callback =
                        { response: CitiesResponse -> DataConverter.citiesResponseToCities(response) }
                    citiesModel.postValue(it.toUIModel(callback))
                }
        }
    }

    private fun initWeatherFlow() {
        viewModelScope.launch {
            weatherFlow.debounce(300).map { getWeather(it) }.onEach {
                weatherModel.postValue(UIModel.Loading(true))
            }.flowOn(Dispatchers.Main)
                .catch { }
                .collect {
//                    val callback = { response: com.natkarock.get_weather.data.WeatherResponse ->
//                        DataConverter.weatherResponseToWeather(response)
//                    }
//                    weatherModel.postValue(it.toUIModel(callback))
                }
        }
    }


    override suspend fun getCities(name: String): ApiResult<CitiesResponse> {
        return citiesRepository.getCities(name)
    }

    override suspend fun getWeather(name: String): ApiResult<com.natkarock.get_weather.data.WeatherResponse> {
        return weatherRepository.getWeather(name)
    }

    override fun setCitiesSearch(text: String) {
        viewModelScope.launch {
            text.let {
                citiesStateFlow.emit(text)
            }
        }
    }


    override fun setWeatherSearch(position: Int) {
        if (citiesModel.value is UIModel.Result) {
            val citiesVal = (citiesModel.value as UIModel.Result).data
            citiesVal.apply {
                if (position < size) {
                    val city = citiesVal[position]
                    city.apply {
                        viewModelScope.launch {
                            weatherFlow.emit(name ?: "")
                        }
                    }
                }
            }
        }

    }


    companion object {
        private const val LOG = "MainViewModel"
    }


}