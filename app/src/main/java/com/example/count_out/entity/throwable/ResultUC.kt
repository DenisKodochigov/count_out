package com.example.count_out.entity.throwable

import com.count_out.domain.entity.throwable.ResultApp
import com.count_out.domain.entity.throwable.ThrowableUC

sealed class ResultUC< out T: Any>: ResultApp {
    data class Success<out T: Any>(val data: T): ResultUC<T>()
    data class Error(val throwable: ThrowableUC): ResultUC<Nothing>()
}