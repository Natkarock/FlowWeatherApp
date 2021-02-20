package com.mobile.finiza.app.data.exception


import java.io.IOException

class NoInternetException(cause: Throwable) : IOException(cause)

class ClientException(val errorTitle: String) : Exception()

class RemoteException : Exception()

class FieldException(val field: String, val fieldErrorCode: String) : Exception()

class ValidationFieldException(val errorTitle: String, val errorList: List<FieldException>) : Exception()

class PagesOverflowException : Exception()

class UnauthorizedException(val errorTitle: String): Exception()

class NicknameNotFoundException(val tokenSocialNetwork: String) : Exception()
