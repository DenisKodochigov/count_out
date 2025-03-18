package com.count_out.presentation.screens.prime

import com.count_out.domain.entity.throwable.ResultUC
import kotlinx.coroutines.flow.MutableStateFlow

abstract class PrimeConvertor<T : Any, R : Any> {
    fun convert(result: ResultUC<T>, state: MutableStateFlow<R>): ScreenState<R> {
        return when (result) {
            is ResultUC.Error -> {
                ScreenState.Error(result.throwable.localizedMessage.orEmpty())
            }
            is ResultUC.Success -> {
                ScreenState.Success(convertSuccess(result.data, state))
            }
        }
    }
    abstract fun convertSuccess(new: T, state: MutableStateFlow<R>): R
}
