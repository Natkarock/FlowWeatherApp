package com.natkarock.core_network.network

import com.google.gson.annotations.SerializedName

class BasicError(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("detail")
    val detail: String = ""
)


data class FieldErrorResponse(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("detail")
    val detail: String = "",
    @SerializedName("invalid-params")
    val invalidParams: List<FieldError> = listOf()
)

data class FieldError(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("code")
    val code: String = ""
)


data class InternalError(
    @SerializedName("title")
    val message: String = "",
    @SerializedName("errorCode")
    val errorCode: String = ""
)

data class UnauthorizedError(
    val message: String = ""
)