package com.natkarock.core.useCase

interface UseCase<T,W> {
    suspend fun setup( loadingCallback: () -> Unit, successCallback: (response: W) -> Unit)
    suspend fun action(data: T)
}