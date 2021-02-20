package com.natkarock.flowweatherapp.model

import java.lang.Exception

sealed class UIModel<out T>{
    data class Loading(val loading: Boolean) : UIModel<Nothing>()
    data class Result<T>(val data: T): UIModel<T>()
    data class Error(val error: Exception): UIModel<Nothing>()
}

