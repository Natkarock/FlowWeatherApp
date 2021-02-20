package com.natkarock.get_weather.di


import com.google.gson.Gson
import com.grapesnberries.curllogger.BuildConfig
import com.grapesnberries.curllogger.CurlLoggerInterceptor
import com.natkarock.core_network.di.WeatherClient
import com.natkarock.core_network.di.WeatherOkHttp
import com.natkarock.get_weather.api.WeatherApi
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
object WeatherNetworkModule {

    private val CURL_TAG = "CURL_TAG"

    @Provides
    @WeatherOkHttp
    @Singleton
    fun provideWeatherOkhttp() :OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            builder.addInterceptor(CurlLoggerInterceptor(CURL_TAG))
        }
        return  builder.build()
    }

    @Provides
    @WeatherClient
    @Singleton
    fun weatherClient(@WeatherOkHttp okHttpClient: OkHttpClient, gson: Gson) =
        Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/").client(okHttpClient).addConverterFactory(
            GsonConverterFactory.create(gson)
        ).build()

    @Provides
    @Singleton
    fun weatherService(@WeatherClient retrofit: Retrofit) = retrofit.create(WeatherApi::class.java)

}