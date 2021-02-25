package com.natkarock.flowweatherapp.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.natkarock.flowweatherapp.model.*
import com.natkarock.core_network.network.data.ApiResult
import com.natkarock.get_cities.data.CitiesResponse
import com.natkarock.get_cities.useCase.GetCities
import com.natkarock.get_weather.data.WeatherResponse
import com.natkarock.get_weather.useCase.GetWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


interface
MainViewModel {
    fun setCitiesSearch(text: String)
    fun setWeatherSearch(position: Int)
    fun setUiWeather(data: ApiResult<WeatherResponse>)
    fun setUiCities(data: ApiResult<CitiesResponse>)
}

@HiltViewModel
class MainViewModelImpl @Inject constructor(
    private val getWeather: GetWeather,
    private val getCities: GetCities,
) : ViewModel(), MainViewModel, BaseViewModel {


    val weatherModel = MutableLiveData<UIModel<Weather>>(UIModel.Loading(false))
    val citiesModel = MutableLiveData<UIModel<List<City>>>(UIModel.Loading(false))


    init {
        initCitiesFlow()
        initWeatherFlow()
    }

    private fun initCitiesFlow() {
        viewModelScope.launch {
            getCities.setup({ citiesModel.postValue(UIModel.Loading(true)) }) { data ->
                setUiCities(data)
            }
        }
    }

    private fun initWeatherFlow() {
        viewModelScope.launch {
            getWeather.setup({ weatherModel.postValue(UIModel.Loading(true)) }) { data ->
                setUiWeather(data)
            }
        }
    }


    override fun setCitiesSearch(text: String) {
        viewModelScope.launch {
            text.let {
                getCities.action(it)
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
                            getWeather.action(name ?: "")
                        }
                    }
                }
            }
        }

    }


    override fun setUiWeather(data: ApiResult<WeatherResponse>) {
        val callback = { response: WeatherResponse ->
            DataConverter.weatherResponseToWeather(response)
        }
        weatherModel.postValue(data.toUIModel(callback))
    }

    override fun setUiCities(data: ApiResult<CitiesResponse>) {
        val callback =
            { response: CitiesResponse -> DataConverter.citiesResponseToCities(response) }
        citiesModel.postValue(data.toUIModel(callback))
    }


    companion object {
        private const val LOG = "MainViewModel"
    }


}