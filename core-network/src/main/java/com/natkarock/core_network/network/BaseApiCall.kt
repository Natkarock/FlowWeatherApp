package com.natkarock.core_network.di.network

import com.natkarock.core_network.network.data.ApiResult
import com.natkarock.flowweatherapp.util.checkResponse
import retrofit2.Response

object BaseApiCall {
    inline fun <reified T : Any> safeApiCall(
        response: Response<T>,
    ): ApiResult<T> {
        return safeApiResult(response)
    }

   inline fun <reified T : Any> safeApiResult(
       response:  Response<T>
    ): ApiResult<T> {
        try {
            return ApiResult.Success(response.checkResponse()!!)
        } catch (e: Exception) {
            return ApiResult.Error(e)
        }
    }
}