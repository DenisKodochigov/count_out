package com.count_out.data.models.throwable

import com.count_out.data.models.throwable.TypeSource.Companion.toRepo
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultApp
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.throwable.ThrowableUC
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

sealed class ResultSource< out T: TypeSource>: ResultApp {
    data class Success<out T: TypeSource>(val data: T): ResultSource<T>()
    data class Error(val throwable: ThrowableDS):ResultSource<Nothing>()

    fun result(content:(TypeSource)-> ResultSource<TypeSource>): ResultSource<TypeSource>{
        return when (this) {
            is Error -> this
            is Success -> { content(this.data) }
        }
    }
    fun nextActionOk( next:()-> Flow<ResultSource<TypeSource>>): Flow<ResultUC<TypeRepo>> {
        return when (this) {
            is Error -> { flowOf(ResultUC.Error(ThrowableUC.extract(throwable)))}
            is Success -> {
                next().map { resultS ->
                    when(resultS){
                        is ResultSource.Error -> ResultUC.Error(ThrowableUC.extract(resultS.throwable))
                        is ResultSource.Success -> { ResultUC.Success(resultS.data.toRepo()) }
                    }
                }
            }
        }
    }
    companion object {

        inline fun <T: TypeSource, R: TypeSource> ResultSource<T>.flatMap(transform: (T) -> ResultSource<R>): ResultSource<R> =
            when (this) {
                is Success -> transform(data)
                is Error -> this
            }

        inline fun <T: TypeSource, R: TypeSource> ResultSource<T>.flatMapFlow(transform: (T) -> Flow<ResultSource<R>>): Flow<ResultSource<R>> =
            when (this) {
                is Success -> transform(data)
                is Error -> flowOf(this)
            }
        inline fun <reified TS : TypeSource> ResultSource<TypeSource>.asType(): ResultSource<TS> =
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

