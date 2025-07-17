package com.count_out.data.source

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ResultSource.Success
import com.count_out.data.models.throwable.ThrowableDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

abstract class SourceData {
    fun <O: Any> Flow<O?>.resultSource(): Flow<ResultSource<O>> {
        var lastValue:O? = null
        return this.filter { it != lastValue }
            .map {
                it?.let {
                    lastValue = it
                    Success(it) as ResultSource<O> }
                    ?: ResultSource.Error(
                        ThrowableDataSource.extractThrowable(Exception("return null"))
                    ) as ResultSource<Nothing>
            }
            .flowOn(Dispatchers.IO)
            .catch { emit(ResultSource.Error(ThrowableDataSource.extractThrowable(it)
            ) as ResultSource<Nothing>) }
    }

    fun <O: Any> getResult(content:()->O?): ResultSource<O> {
        return try {
            content()?.let { Success(it) as ResultSource<O>
                } ?: ResultSource.Error(
                    ThrowableDataSource.extractThrowable(Exception("return null"))
                ) as ResultSource<Nothing>
            }
            catch (e: Exception) {
                ResultSource.Error(ThrowableDataSource.extractThrowable(e)) as ResultSource<Nothing>
            }

    }
    fun <O: Any> resultNullException(): ResultSource<O> {
        return ResultSource.Error(
            ThrowableDataSource.extractThrowable(Exception("return null"))
        ) as ResultSource<Nothing>
    }
}

//try {
//    val result = executeData()
//    if (result != null) {
//        ResultDataSource.Success(result) as ResultDataSource<O>
//    } else {
//        ResultDataSource.Error(ThrowableDataSource.extractThrowable(Exception("return null")))
//    }
//} catch (e: Exception) {
//    ResultDataSource.Error(ThrowableDataSource.extractThrowable(e))
//}

//override fun getLastUsedPlan(): Flow<ResultUC<Training>> {
//    return source.getLastPlan().map { resultDataSource->
//        converter.execute(when(resultDataSource){
//            is ResultDataSource.Success-> sourceTraining.get2( resultDataSource.data)
//            is ResultDataSource.Error -> resultDataSource
//        })
//    }
//}