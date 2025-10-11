package com.count_out.domain.entity.throwable

sealed class ResultDomain< out T: Any> {
    data class Success<out T: Any>(val data: T): ResultDomain<T>()
    data class Error(val throwable: ThrowableUC):ResultDomain<Nothing>()

    fun chek(): ResultDomain<T>?{
        return this as? Success
    }
}