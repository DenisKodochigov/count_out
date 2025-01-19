package com.example.data.entity

import com.example.data.throwable.Result
import com.example.data.throwable.ThrowableSD
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

abstract class SourceData <I: SourceData.Request, O: SourceData.Response>(
    private val configuration: Configuration
) {
    fun execute(input: I): Flow<Result<O>> = executeData(input)
        .map { Result.Success(it) as Result<O> }
        .flowOn(configuration.dispatcher)
        .catch { emit(Result.Error(ThrowableSD.extractThrowable(it)) as Result<Nothing>) }
    class Configuration(val dispatcher: CoroutineDispatcher)
    internal abstract fun executeData(input: I): Flow<O>
    interface Request
    interface Response
}