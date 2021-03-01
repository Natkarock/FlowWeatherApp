package com.natkarock.core_network.util

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.natkarock.core_network.network.BasicError
import com.natkarock.core_network.network.FieldErrorResponse
import com.natkarock.core_network.network.UnauthorizedError
import com.natkarock.core_network.network.RestExceptionFactory
import com.natkarock.core_network.network.data.ApiResult
import retrofit2.Response
import java.net.HttpURLConnection



inline fun <reified T> Response<T>.checkResponse(): T? {
    Log.i("ErrorBody", errorBody().toString())

    if (isSuccessful) return body()

    if (body() == null && errorBody() == null) {
        throw RuntimeException("Both body() and errorBody() are null")
    } else {
        if (errorBody() != null) {
            val code = code()
            val errorBody = errorBody()?.string()

            val errorResponse: Any? = when {
                code == HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    val typeToken = TypeToken.get(UnauthorizedError::class.java).type
                    Gson().fromJson<UnauthorizedError>(errorBody, typeToken)
                }
                code in 400..499 -> {
                    if (errorBody?.contains("code") == true) {
                        val typeToken = TypeToken.get(FieldErrorResponse::class.java).type
                        Gson().fromJson<FieldErrorResponse>(errorBody, typeToken)
                    } else {
                        val typeToken = TypeToken.get(BasicError::class.java).type
                        Gson().fromJson<BasicError>(errorBody, typeToken)
                    }
                }

                code >= 500 -> {
                    val typeToken = TypeToken.get(InternalError::class.java).type
                    Gson().fromJson<InternalError>(errorBody, typeToken)
                }
                else -> null
            }

            RestExceptionFactory.checkErrorList(errorResponse)
        }
    }

    return body()
}

fun <T>checkResult(result: ApiResult<T>, callback: (T) -> Unit ){
    when(result){
        is ApiResult.Error -> Log.i("", "")//error.postValue(result)
        is ApiResult.Success -> callback.invoke(result.data)
    }
}
