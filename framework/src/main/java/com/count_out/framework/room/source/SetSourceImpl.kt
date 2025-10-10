package com.count_out.framework.room.source

import com.count_out.data.models.Data
import com.count_out.data.models.throwable.ResultData
import com.count_out.data.models.throwable.ResultData.Companion.flatMap
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.types_data.LongDb
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.SetSource
import com.count_out.framework.room.db.set.SetDao
import com.count_out.framework.room.db.set.SetTb
import javax.inject.Inject

class SetSourceImpl @Inject constructor(
    private val speechSource: SpeechSourceImpl,
    private val dao: SetDao
): SetSource, PrimeSource() {

    override fun copy(set: Data): ResultData<Data> =
        set.safeUse<SetTb, ResultData<Data>> { setTb ->
            dao.insert( setTb.copy(idSet = 0L)).result().flatMap { ownerId->
                val listSpeech = speechSource.getListSpeech(setId = setTb.idSet)
                        .map { it.apply { setId = (ownerId as LongDb).item } }
                if (speechSource.insert(listSpeech).count() == listSpeech.count())
                    ResultData.Success(LongDb(listSpeech.count().toLong()))
                else ResultData.Error(ThrowableDS.RequestFailed())
            }
        }

    override fun del(set: Data): ResultData<Data> =
        set.safeUse<SetTb, Long> { item -> dao.delete(item).toLong() }

    override fun update(set: Data): ResultData<Data> =
        set.safeUse<SetTb, Long> { setTb -> dao.update(setTb).toLong() }
}