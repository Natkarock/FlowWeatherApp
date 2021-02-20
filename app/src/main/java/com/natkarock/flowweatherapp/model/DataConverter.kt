package com.natkarock.flowweatherapp.model

import com.natkarock.get_cities.data.Data
import com.natkarock.get_weather.data.WeatherResponse


fun Data.toCity(): City = City(
    name = name,
    city = city,
    region = region,
    regionCode = regionCode,
    country = country,
    countryCode = countryCode,
    id = id,
    latitude = latitude,
    longitude = longitude,
    type = type,
    wikiDataId = wikiDataId
)

fun com.natkarock.get_weather.data.WeatherResponse.toWeather(): Weather = Weather(
    feels_like = main.feels_like,
    humidity = main.humidity,
    pressure = main.pressure,
    temp = main.temp,
    temp_max = main.temp_max,
    temp_min = main.temp_min,
    city = name
)


object DataConverter {

    fun weatherResponseToWeather(response: com.natkarock.get_weather.data.WeatherResponse): Weather = response.toWeather()

    fun citiesResponseToCities(response: com.natkarock.get_cities.data.CitiesResponse): List<City> =
        response.data.map { it.toCity() }

    fun citiesToStrings(cities: List<City>): List<String> =
        cities.map { city -> city.name ?: "Default" }
}


