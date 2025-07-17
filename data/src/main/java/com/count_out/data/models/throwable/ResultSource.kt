package com.count_out.data.models.throwable

import com.count_out.domain.entity.throwable.ResultApp

sealed class ResultSource< out T: Any>: ResultApp {
    data class Success<out T: Any>(val data: T): ResultSource<T>()
    data class Error(val throwable: ThrowableDataSource):ResultSource<Nothing>()
    fun getSuccess(): T?{
        return when (this){
            is Success -> this.data
            is Error -> null
        }
    }
}