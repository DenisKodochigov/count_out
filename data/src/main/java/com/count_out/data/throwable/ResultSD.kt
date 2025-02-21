package com.count_out.data.throwable

import com.count_out.domain.entity.throwable.ResultApp

sealed class ResultSD< out T: Any>:ResultApp {
    data class Success<out T: Any>(val data: T): ResultSD<T>()
    data class Error(val throwable: ThrowableSD): ResultSD<Nothing>()
}
