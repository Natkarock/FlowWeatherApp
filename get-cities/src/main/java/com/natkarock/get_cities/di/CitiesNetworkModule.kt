package com.natkarock.get_cities.di

import com.google.gson.Gson
import com.grapesnberries.curllogger.BuildConfig
import com.grapesnberries.curllogger.CurlLoggerInterceptor
import com.natkarock.core_network.di.CitiesClient
import com.natkarock.core_network.di.CitiesOkHttp
import com.natkarock.get_cities.api.CitiesApi
import com.natkarock.get_cities.network.CitiesApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CitiesNetworkModule {

    private val CURL_TAG = "CURL_TAG"

    @Provides
    @CitiesOkHttp
    @Singleton
    fun provideCitiesOkhttp(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            builder.addInterceptor(CurlLoggerInterceptor(CURL_TAG))
        }
        builder.addInterceptor(CitiesApiKeyInterceptor())
        return builder.build()
    }

    @Provides
    @CitiesClient
    @Singleton
    fun citiesClient(@CitiesOkHttp okHttpClient: OkHttpClient, gson: Gson) =
        Retrofit.Builder().baseUrl("https://wft-geo-db.p.rapidapi.com/v1/geo/").client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(gson)
            ).build()

    @Provides
    fun citiesService(@CitiesClient retrofit: Retrofit) = retrofit.create(CitiesApi::class.java)

}