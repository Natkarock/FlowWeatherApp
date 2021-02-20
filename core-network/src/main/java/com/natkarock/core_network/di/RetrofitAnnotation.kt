package com.natkarock.core_network.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CitiesClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WeatherClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class  CitiesOkHttp


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class  WeatherOkHttp