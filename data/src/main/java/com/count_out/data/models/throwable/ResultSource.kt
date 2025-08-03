package com.count_out.data.models.throwable

import com.count_out.domain.entity.throwable.ResultApp
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.throwable.ThrowableUC
import com.count_out.domain.repository.TypeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

sealed class ResultSource< out T: TypeSource>: ResultApp {
    data class Success<out T: TypeSource>(val data: T): ResultSource<T>()
    data class Error(val throwable: ThrowableDS):ResultSource<Nothing>()

    fun nextAction( next:(TypeSource)-> Flow<ResultSource<TypeSource>>): Flow<ResultUC<TypeRepo>> {
        return when (this) {
            is Error -> { flow { emit(ResultUC.Error(ThrowableUC.extract(throwable))) } }
            is Success -> {
                next(this.data).map { resultS ->
                    when(resultS){
                        is Error -> ResultUC.Error(ThrowableUC.extract(resultS.throwable))
                        is Success -> { ResultUC.Success(resultS.data.toRepo()) }
                    }
                }
            }
        }
    }
    fun result(content:(TypeSource)-> ResultSource<TypeSource>): ResultSource<TypeSource>{
        return when (this) {
            is Error -> this
            is Success -> { content(this.data) }
        }
    }
    fun resultOK(): Boolean{
        return when(this){
            is Error -> false
            is Success ->  {(this.data as TypeSource.IntT).item > 0}
        }
    }
    fun resultLong(): Long{
        return when(this){
            is Error -> 0L
            is Success ->  {(this.data as TypeSource.LongT).item }
        }
    }
    fun resultString(): String{
        return when(this){
            is Error -> ""
            is Success ->  {(this.data as TypeSource.StringT).item }
        }
    }
    fun resultBoolean(): Boolean{
        return when(this){
            is Error -> false
            is Success ->  {(this.data as TypeSource.BooleanT).item }
        }
    }
    fun nextActionOk( next:()-> Flow<ResultSource<TypeSource>>): Flow<ResultUC<TypeRepo>> {
        return when (this) {
            is Error -> { flow { emit(ResultUC.Error(ThrowableUC.extract(throwable))) } }
            is Success -> {
                next().map { resultS ->
                    when(resultS){
                        is Error -> ResultUC.Error(ThrowableUC.extract(resultS.throwable))
                        is Success -> { ResultUC.Success(resultS.data.toRepo()) }
                    }
                }
            }
        }
    }
}