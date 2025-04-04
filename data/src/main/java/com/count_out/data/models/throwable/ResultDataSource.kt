package com.count_out.data.models.throwable

import com.count_out.domain.entity.throwable.ResultApp

sealed class ResultDataSource< out T: Any>: ResultApp {
    data class Success<out T: Any>(val data: T): ResultDataSource<T>()
    data class Error(val throwable: ThrowableDataSource):ResultDataSource<Nothing>()
}