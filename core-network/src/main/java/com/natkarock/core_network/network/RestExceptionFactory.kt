package com.natkarock.core_network.network

import android.os.RemoteException
import com.mobile.finiza.app.data.exception.ClientException
import com.mobile.finiza.app.data.exception.UnauthorizedException
import com.mobile.finiza.app.data.remote.BasicError
import com.mobile.finiza.app.data.remote.InternalError
import com.mobile.finiza.app.data.remote.UnauthorizedError

object RestExceptionFactory {

    fun checkErrorList(error: Any?) {
        throw when (error) {
            is UnauthorizedError -> UnauthorizedException(error.message)
            is BasicError -> ClientException(
                error.message ?: error.detail
            )
            is InternalError -> ClientException(
                error.errorCode
            )
            else -> RemoteException()
        }
    }

}