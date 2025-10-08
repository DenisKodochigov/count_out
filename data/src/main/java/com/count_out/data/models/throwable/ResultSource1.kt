package com.count_out.data.models.throwable

import com.count_out.data.models.Data
import com.count_out.domain.entity.throwable.ResultApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

sealed class ResultSource1< out T: Data>: ResultApp {
    data class Success<out T: Data>(val data: T): ResultSource1<T>()
    data class Error(val throwable: ThrowableDS):ResultSource1<Nothing>()

    fun result(content:(Data)-> ResultSource1<Data>): ResultSource1<Data>{
        return when (this) {
            is Error -> this
            is Success -> { content(this.data) }
        }
    }
//    fun nextActionOk( next:()-> Flow<ResultSource1<Tb>>): Flow<ResultUC<Element>> {
//        return when (this) {
//            is Error -> { flowOf(ResultUC.Error(ThrowableUC.extract(throwable)))}
//            is Success -> {
//                next().map { resultS ->
//                    when(resultS){
//                        is Error -> ResultUC.Error(ThrowableUC.extract(resultS.throwable))
//                        is Success -> { ResultUC.Success(resultS) }
//                    }
//                }
//            }
//        }
//    }
    companion object {
        inline fun <T: Data, R: Data> ResultSource1<T>.flatMap(transform: (T) -> ResultSource1<R>): ResultSource1<R> =
            when (this) {
                is Success -> transform(data)
                is Error -> this
            }

        inline fun <T: Data, R: Data> ResultSource1<T>.flatMapFlow(transform: (T) -> Flow<ResultSource1<R>>): Flow<ResultSource1<R>> =
            when (this) {
                is Success -> transform(data)
                is Error -> flowOf(this)
            }
        inline fun <reified TS : Data> ResultSource1<Data>.asType(): ResultSource1<TS> =
            when (this) {
                is Success -> {
                    val typed = data as? TS
                    if (typed != null) Success(typed)
                    else Error(ThrowableDS.NotValidType())
                }
                is Error -> this
            }
    }
}

