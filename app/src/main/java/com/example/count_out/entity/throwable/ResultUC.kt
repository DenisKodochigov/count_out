package com.count_out.domain.entity.throwable

sealed class ResultUC< out T: Any>: ResultApp {
    data class Success<out T: Any>(val data: T): ResultUC<T>()
    data class Error(val throwable: ThrowableUC):ResultUC<Nothing>()
}