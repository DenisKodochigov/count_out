package com.count_out.presentation.screens.prime

import com.count_out.domain.entity.throwable.ResultUC
import kotlinx.coroutines.flow.MutableStateFlow

abstract class PrimeConvertor<D : Any, T : Any> {
    abstract fun convertSuccess(new: D, state: MutableStateFlow<T>): T
    fun convert(result: ResultUC<D>, state: MutableStateFlow<T>): ScreenState<T> {
        return when (result) {
            is ResultUC.Error -> { ScreenState.Error(result.throwable.localizedMessage.orEmpty()) }
            is ResultUC.Success -> { ScreenState.Success(convertSuccess(result.data, state)) }
        }
    }
}
