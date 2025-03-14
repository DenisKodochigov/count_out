package com.count_out.presentation.screens.prime

import com.count_out.domain.entity.throwable.ResultUC

abstract class PrimeConvertor<T : Any, R : Any> {
    fun convert(result: ResultUC<T>): ScreenState<R> {
        return when (result) {
            is ResultUC.Error -> {
                ScreenState.Error(result.throwable.localizedMessage.orEmpty())
            }
            is ResultUC.Success -> {
                ScreenState.Success(convertSuccess(result.data))
            }
        }
    }
    abstract fun convertSuccess(data: T): R
}
