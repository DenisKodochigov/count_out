package com.count_out.data.source

import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ResultSource.Success
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

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
//            } ResultUC
//            .flowOn(Dispatchers.IO)
//            .catch { emit(ResultSource.Error(ThrowableDS.RequestFailed())) }
//    }
}