package com.count_out.presentation.screens.prime

import android.util.Log
import com.count_out.domain.entity.throwable.ResultUC
import kotlinx.coroutines.flow.MutableStateFlow

abstract class PrimeConvertor<D : Any, T : Any> {
    abstract fun makeSuccess(resultData: D, state: MutableStateFlow<T>): T
    fun make(result: ResultUC<D>, state: MutableStateFlow<T>): ScreenState<T> {
        return when (result) {
            is ResultUC.Error -> {
                Log.d("KDS", "ERROR ${result.throwable}")
                ScreenState.Error(result.throwable.localizedMessage.orEmpty()) }
            is ResultUC.Success -> { ScreenState.Success(makeSuccess(result.data, state)) }
        }
    }

}
