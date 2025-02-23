package com.count_out.data.models

import com.count_out.data.throwable.ResultSD
import com.count_out.data.throwable.ThrowableSD
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

abstract class SourceData <I: SourceData.Request, O: SourceData.Response>(
    private val configuration: Configuration
) {
    fun execute(input: I): Flow<ResultSD<O>> = executeData(input)
        .map { ResultSD.Success(it) as ResultSD<O> }
        .flowOn(configuration.dispatcher)
        .catch { emit(ResultSD.Error(ThrowableSD.extractThrowable(it)) as ResultSD<Nothing>) }
    class Configuration(val dispatcher: CoroutineDispatcher)
    internal abstract fun executeData(input: I): Flow<O>
    interface Request
    interface Response
}