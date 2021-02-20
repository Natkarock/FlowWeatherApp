package com.natkarock.get_cities.api

import com.natkarock.get_cities.data.CitiesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CitiesApi {
    @GET("cities")
    suspend fun getCities(@Query(value = "namePrefix") name: String): Response<CitiesResponse>
}