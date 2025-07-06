package com.count_out.data.source

import com.count_out.data.models.throwable.ResultDataSource
import com.count_out.data.models.throwable.ResultDataSource.Success
import com.count_out.data.models.throwable.ThrowableDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

abstract class SourceData {
    fun <O: Any> getResultFlow(content: () -> Flow<O?>): Flow<ResultDataSource<O>> {
        var lastValue:O? = null
        return content().filter { it != lastValue }
            .map {
                it?.let {
                    lastValue = it
                    Success(it) as ResultDataSource<O> }
                ?: ResultDataSource.Error(
                    ThrowableDataSource.extractThrowable(Exception("return null"))
                ) as ResultDataSource<Nothing>
            }
            .flowOn(Dispatchers.IO)
            .catch { emit(ResultDataSource.Error(ThrowableDataSource.extractThrowable(it)
                                                        ) as ResultDataSource<Nothing>) }
    }

    fun <O: Any> getResult(content:()->O?): ResultDataSource<O> {
        return try {
            content()?.let { Success(it) as ResultDataSource<O>
                } ?: ResultDataSource.Error(
                    ThrowableDataSource.extractThrowable(Exception("return null"))
                ) as ResultDataSource<Nothing>
            }
            catch (e: Exception) {
                ResultDataSource.Error(ThrowableDataSource.extractThrowable(e)) as ResultDataSource<Nothing>
            }

    }
    fun <O: Any> resultNullException(): ResultDataSource<O> {
        return ResultDataSource.Error(
            ThrowableDataSource.extractThrowable(Exception("return null"))
        ) as ResultDataSource<Nothing>
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