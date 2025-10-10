package com.count_out.data.models.throwable

import com.count_out.data.models.Data
import com.count_out.domain.entity.throwable.ResultApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

sealed class ResultData< out T: Data>: ResultApp {
    data class Success<out T: Data>(val data: T): ResultData<T>()
    data class Error(val throwable: ThrowableDS):ResultData<Nothing>()

    fun result(content:(Data)-> ResultData<Data>): ResultData<Data>{
        return when (this) {
            is Error -> this
            is Success -> { content(this.data) }
        }
    }

    companion object {
        inline fun <T: Data, R: Data> ResultData<T>.flatMap(transform: (T) -> ResultData<R>): ResultData<R> =
            when (this) {
                is Success -> transform(data)
                is Error -> this
            }

        inline fun <T: Data, R: Data> ResultData<T>.flatMapFlow(transform: (T) -> Flow<ResultData<R>>): Flow<ResultData<R>> =
            when (this) {
                is Success -> transform(data)
                is Error -> flowOf(this)
            }
    }
}
//        inline fun <reified TS : Data> ResultSource<Data>.asType(): ResultSource<TS> =
//            when (this) {
//                is Success -> {
//                    val typed = data as? TS
//                    if (typed != null) Success(typed)
//                    else Error(ThrowableDS.NotValidType())
//                }
//                is Error -> this
//            }
//    fun nextActionOk( next:()-> Flow<ResultSource<Data>>): Flow<ResultUC<TypeRepo>> {
//        return when (this) {
//            is Error -> { flowOf(ResultUC.Error(ThrowableUC.extract(throwable)))}
//            is Success -> {
//                next().map { resultS ->
//                    when(resultS){
//                        is ResultSource.Error -> ResultUC.Error(ThrowableUC.extract(resultS.throwable))
//                        is ResultSource.Success -> { ResultUC.Success(resultS.data.toRepo()) }
//                    }
//                }
//            }
//        }
//    }