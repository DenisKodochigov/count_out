package com.example.count_out.ui.screens.prime

import com.example.count_out.R
import com.example.count_out.entity.throwable.ResultUC

abstract class PrimeConvertor<T : Any, R : Any> {
    fun convert(result: ResultUC<T>): ScreenState<R> {
        return when (result) {
            is ResultUC.Error -> { ScreenState.Error(result.throwable.localizedMessage.orEmpty()) }
            is ResultUC.Success -> { ScreenState.Success(convertSuccess(result.data)) }
        }
    }
    abstract fun convertSuccess(data: T): R
}
interface PrimeConv<T : Any, R : Any> {
    fun convert(result: ResultUC<T>): ScreenState<R> {
        return when (result) {
            is ResultUC.Error -> { ScreenState.Error(result.throwable.localizedMessage.orEmpty()) }
            is ResultUC.Success -> { ScreenState.Success(convertSuccess(result.data)) }
        }
    }
    fun convertSuccess(data: T): R
}