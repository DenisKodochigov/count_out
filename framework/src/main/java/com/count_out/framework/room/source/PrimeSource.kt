package com.count_out.framework.room.source

import android.util.Log
import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.ResultData.Companion.flatMapCondition
import com.count_out.data.models.entity.ActivityDb
import com.count_out.data.models.entity.BooleanDb
import com.count_out.data.models.entity.LongDb
import com.count_out.data.models.entity.LongsDb
import com.count_out.data.models.entity.NameIdDb
import com.count_out.data.models.entity.PlanDb
import com.count_out.data.models.entity.SpeechesDb
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.framework.di.lg
import com.count_out.framework.room.db.speech.SpeechTb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

abstract class PrimeSource {

    fun <T : Data>Long.longToResult(number: Long = 0L, success: (Long) -> T): ResultData<T> =
        if (this > number) ResultData.Success(success(this))
        else ResultData.Error(ThrowableDS.RequestFailed())

    fun <T: Data> List<Long>.listToResult(success: (List<Long>) -> T): ResultData<T> =
        ResultData.Success(success(this))

    inline fun <reified D: Data, R> Data.safeUse(crossinline block: (D) -> R): ResultData<Data> =
        runCatching  {
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
        }.getOrElse  { ResultData.Error(ThrowableDS.Companion.extract(it)) }

    inline fun <reified D: Data, R> Data.safeUseFlow(crossinline block: (D) -> Flow<R>): Flow<ResultData<Data>> {
        return if (this is D) {
            block(this)
                .filterNotNull()
                .map { result ->
                    when (result) {
                        is Long -> {
                            if (result > 0L) ResultData.Success(LongDb(result) as Data)
                            else ResultData.Error(ThrowableDS.RequestFailed())
                        }
                        is Boolean -> {
                            if (result) ResultData.Success(BooleanDb(result) as Data)
                            else ResultData.Error(ThrowableDS.RequestFailed())
                        }
                        is ActivityDb -> ResultData.Success(result)
                        is PlanDb -> ResultData.Success(result)
                        is NameIdDb -> ResultData.Success(result)
                        is ResultData<Data> -> result
                        else -> ResultData.Success(LongDb(0L))
                    }
                }
                .flowOn(Dispatchers.IO)
                .catch { ResultData.Error(ThrowableDS.Companion.extract(it)) }
        }
        else flowOf(ResultData.Error(ThrowableDS.NotValidType()))}

    inline fun <T : Data> copyWithDependencies(
        original: T,
        insertMain: (T) -> Long,
        getSpeeches: () -> List<SpeechTb>,
        insertSpeeches: (List<SpeechTb>) -> List<Long>,
        copyNested: (id: Long) -> ResultData<LongDb>
    ): ResultData<Data> {
        val newId = insertMain(original)
        return newId.longToResult { LongDb(it) }
            .flatMapCondition({ it.item.count() > 0L }) { newId1 ->
                getSpeeches().map { it.apply { ringId = newId1.item } }
                    .let { SpeechesDb(item = it).toResultData() }
            }
            .flatMapCondition({ vl -> vl.item.isNotEmpty() }) { speeches ->
                insertSpeeches(speeches.item.map { it as SpeechTb }).listToResult { LongsDb(it) }
            }
            .flatMapCondition<LongsDb, LongDb>({ vl -> vl.item > 0L }) {
                copyNested(newId)
            }
    }
}