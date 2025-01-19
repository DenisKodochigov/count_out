package com.example.domain.entity.throwable

sealed class Result< out T: Any> {
    data class Success<out T: Any>(val data: T): Result<T>()
    data class Error(val throwable: ThrowableUC):Result<Nothing>()
}
