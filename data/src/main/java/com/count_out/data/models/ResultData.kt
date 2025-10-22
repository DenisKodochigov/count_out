package com.count_out.data.models

import android.util.Log
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.throwable.ThrowableUC
import com.count_out.domain.entity.workout.Domain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

sealed class ResultData< out T: Data> {
    data class Success<out T: Data>(val data: T): ResultData<T>()
    data class Error(val throwable: ThrowableDS):ResultData<Nothing>()

    companion object {
        inline fun <T: Data, R: Data> ResultData<T>.flatMap(transform: (T) -> ResultData<R>): ResultData<R> =
            when (this) {
                is Success -> transform(data)
                is Error -> this
            }

        fun Flow<ResultData<Data>>.convertorFlow(): Flow<ResultDomain<Domain>> {
            return this.filterNotNull().map { resultS->
                when(resultS){
                    is Error -> ResultDomain.Error(ThrowableUC.Companion.extract(resultS.throwable))
                    is Success -> { ResultDomain.Success(resultS.data.toDomain())
                    }
                }
            }
        }

        fun ResultData<Data>.convertor(): Flow<ResultDomain<Domain>> {
            return flowOf(
                when (this) {
                    is Error -> ResultDomain.Error(ThrowableUC.Companion.extract(this.throwable))
                    is Success -> ResultDomain.Success(this.data.toDomain())
                }
            )
        }

        inline fun <T: Data, R: Data> ResultData<T>.flatMapFlow(transform: (T) -> Flow<ResultData<R>>): Flow<ResultData<R>> =
            when (this) {
                is Success -> transform(data)
                is Error -> flowOf(this)
            }

        inline fun <T: Data, R: Data> ResultData<T>.flatMapCondition(
            condition:(R)-> Boolean, transform: (T) -> ResultData<R>): ResultData<R> =
            when (this) {
                is Error -> this
                is Success -> {
                    transform(data).let{ result->
                        if (result is Success){
                            if ( condition(result.data)) result
                            else Error(ThrowableDS.RequestFailed())
                        } else result
                    }
                }
            }
    }
}