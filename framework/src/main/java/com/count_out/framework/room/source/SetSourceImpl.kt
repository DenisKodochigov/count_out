package com.count_out.framework.room.source

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.entity.LongDb
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.source.room.SetSource
import com.count_out.framework.room.db.set.SetDao
import com.count_out.framework.room.db.set.SetTb
import javax.inject.Inject

class SetSourceImpl @Inject constructor(
    private val speechSource: SpeechSourceImpl,
    private val dao: SetDao
): SetSource, PrimeSource() {
    override fun copy (set: Data): ResultData<Data> = runCatching {
        copyWithDependencies(
            original = (set as SetTb),
            originalId = set.idSet,
            insertMain = { dao.insert(it.copy(idSet = 0L)) },
            getSpeeches = { oldId -> speechSource.getListSpeech(setId = oldId) },
            insertSpeeches = { speechSource.insert(it) },
            copyNested = { id -> ResultData.Success(LongDb(1))}
        )
    }.getOrElse { ResultData.Error(ThrowableDS.extract(it)) }

    override fun del(set: Data): ResultData<Data> =
        set.safeUse<SetTb, Long> { item -> dao.delete(item).toLong() }

    override fun update(set: Data): ResultData<Data> =
        set.safeUse<SetTb, Long> { setTb -> dao.update(setTb).toLong() }
}

//    override fun copy1(set: Data): ResultData<Data> =
//        set.safeUse<SetTb, ResultData<Data>> { setTb ->
//            dao.insert( setTb.copy(idSet = 0L)).longToResult().flatMap { ownerId->
//                val listSpeech = speechSource.getListSpeech(setId = setTb.idSet)
//                        .map { it.apply { setId = (ownerId as LongDb).item } }
//                if (speechSource.insert(listSpeech).count() == listSpeech.count())
//                    ResultData.Success(LongDb(listSpeech.count().toLong()))
//                else ResultData.Error(ThrowableDS.RequestFailed())
//            }
//        }