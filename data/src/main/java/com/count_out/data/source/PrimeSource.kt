package com.count_out.data.source

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.TypeSource

abstract class PrimeSource {
    inline fun <reified TS : TypeSource> ResultSource<TypeSource>.getTyped(): TS? =
        (this as? ResultSource.Success)?.data as? TS

    //###################################################################################
//    fun Flow<TypeSource?>.resultSource(): Flow<ResultSource<TypeSource>> {
//        var lastValue: TypeSource? = null
//        return this.filter { it != lastValue }
//            .map {
//                lastValue = it
//                ResultSource.Success(it) as ResultSource<TypeSource>
//            }
//            .flowOn(Dispatchers.IO)
//            .catch { emit(ResultSource.Error(ThrowableDS.RequestFailed())) }
//    }
}