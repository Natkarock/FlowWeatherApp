package com.natkarock.flowweatherapp.model

import com.natkarock.core_network.network.data.ApiResult
import com.natkarock.flowweatherapp.model.UIModel


fun <T, W> ApiResult<T>.toUIModel(callback: (T) -> W): UIModel<W> {
    return when (this) {
        is ApiResult.Error -> UIModel.Error(exception)
        is ApiResult.Success<T> -> UIModel.Result(callback(data))
    }
}