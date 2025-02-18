package com.example.data.throwable

import com.example.domain.entity.throwable.ResultApp

sealed class ResultSD< out T: Any>:ResultApp {
    data class Success<out T: Any>(val data: T): ResultSD<T>()
    data class Error(val throwable: ThrowableSD): ResultSD<Nothing>()
}
