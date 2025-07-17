package com.count_out.data.repository

import com.count_out.data.entity.ConverterResult
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ResultSource.Success
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.throwable.ThrowableUC
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

abstract class PrimeRepo {
    abstract fun converter(): ConverterResult

    @OptIn(ExperimentalCoroutinesApi::class)
    fun <T : Any, O: Any> Flow<ResultSource<T>>.concat(action: (T)-> Flow<ResultSource<O>>,): Flow<ResultUC<O>> {
        return this.flatMapConcat { resultDS ->
            when (resultDS) {
                is ResultSource.Error -> flow { emit(converter().execute(resultDS)) }
                is ResultSource.Success -> {
                    action(resultDS.data).map { converter().execute(it) }
                }
            }
        }
    }
    fun <O: Any> Flow<ResultSource<O>?>.resultUC(): Flow<ResultUC<O>> {
        var lastValue:O? = null
        return this.filter { it != lastValue }
            .map{resultSource->
                resultSource?.let { resultS->
                    when(resultS){
                        is ResultSource.Error -> ResultUC.Error(
                            ThrowableUC.extractThrowable(Exception("return null")))
                        is Success -> {
                            lastValue = resultS.data as O?
                            ResultUC.Success(resultS.data) as ResultUC<O>
                        }
                    }
                } ?: ResultUC.Error(
                        ThrowableUC.extractThrowable(Exception("return null"))
                    ) as ResultUC<Nothing>
            }
            .flowOn(Dispatchers.IO)
            .catch { emit(ResultUC.Error(ThrowableUC.extractThrowable(it)
            ) as ResultUC<Nothing>) }
    }
}