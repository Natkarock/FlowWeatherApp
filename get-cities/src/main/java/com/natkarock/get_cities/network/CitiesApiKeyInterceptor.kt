package com.natkarock.get_cities.network

import okhttp3.Interceptor
import okhttp3.Response

class CitiesApiKeyInterceptor: Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder().header(API_KEY_HEADER_NAME, API_KEY)
        val newRequest = requestBuilder.build()
        return  chain.proceed(newRequest)

    }

    companion object {
        private  const val API_KEY_HEADER_NAME = "X-RapidAPI-Key"
        private  const val API_KEY ="4caaa57238msh4c89654bae00cf1p17963djsn4c4b8b0c79c2"
    }

}