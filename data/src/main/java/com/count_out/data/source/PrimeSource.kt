package com.count_out.data.source

import android.util.Log.e
import com.count_out.data.models.Data
import com.count_out.data.models.NameIdDb
import com.count_out.data.models.PlanDb
import com.count_out.data.models.throwable.ResultData
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.models.types_data.BooleanDb
import com.count_out.data.models.types_data.LongDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

//import com.count_out.data.models.Data
//import com.count_out.data.models.throwable.ResultData
//import com.count_out.data.models.throwable.TypeSource

abstract class PrimeSource {
    inline fun Data.useLong(crossinline block: (Long) -> Long): ResultData<Data> =
        if (this is LongDb) {
            try { block(this.item).result() }
            catch (e: Exception) { ResultData.Error(ThrowableDS.extract(e)) }
        } else ResultData.Error(ThrowableDS.NotValidType())

    fun Long.result(): ResultData<Data> =
        if (this > 0L) ResultData.Success(LongDb(this))
        else ResultData.Error(ThrowableDS.RequestFailed())

    inline fun <reified D: Data, R> Data.safeUse(crossinline block: (D) -> R): ResultData<Data> =
        try {
            if (this is D) block(this).let { result->
                when(result){
                    is Long-> {
                        if (result > 0L) ResultData.Success(LongDb(result) as Data)
                        else ResultData.Error(ThrowableDS.RequestFailed())
                    }
                    is Boolean->{
                        if (result) ResultData.Success(BooleanDb(result) as Data)
                        else ResultData.Error(ThrowableDS.RequestFailed())
                    }
                    is NameIdDb -> ResultData.Success(result)
                    is ResultData<Data> -> result
                    else -> ResultData.Success(LongDb(0L))
                }
            }
            else ResultData.Error(ThrowableDS.NotValidType())
        } catch (e: Exception) { ResultData.Error(ThrowableDS.extract(e)) }

    inline fun <reified D: Data, R> Data.safeUseFlow(crossinline block: (D) -> Flow<R>): Flow<ResultData<Data>> =
        if (this is D) {
            block(this).filterNotNull().map { result ->
                when (result) {
                    is Long -> {
                        if (result > 0L) ResultData.Success(LongDb(result) as Data)
                        else ResultData.Error(ThrowableDS.RequestFailed())
                    }
                    is Boolean -> {
                        if (result) ResultData.Success(BooleanDb(result) as Data)
                        else ResultData.Error(ThrowableDS.RequestFailed())
                    }
                    is PlanDb -> ResultData.Success(result)
                    is NameIdDb -> ResultData.Success(result)
                    is ResultData<Data> -> result
                    else -> ResultData.Success(LongDb(0L))
                }
            }
        }
        else flowOf(ResultData.Error(ThrowableDS.NotValidType()))

    inline fun <reified TS : TypeSource> ResultData<Data>.getTyped(): TS? =
        (this as? ResultData.Success)?.data as? TS

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