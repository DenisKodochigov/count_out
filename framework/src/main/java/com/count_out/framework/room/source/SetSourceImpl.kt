package com.count_out.framework.room.source

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.entity.LongDb
import com.count_out.data.models.entity.SetDb
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.source.room.SetSource
import com.count_out.framework.room.db.set.SetDao
import com.count_out.framework.room.db.set.SetTb.Companion.toTb
import javax.inject.Inject

class SetSourceImpl @Inject constructor(
    private val speechSource: SpeechSourceImpl,
    private val dao: SetDao
): SetSource, PrimeSource() {
    override fun insert (set: Data): ResultData<Data> = runCatching {
        return if (set is SetDb){
            copyWithDependencies(
                insertMain = { dao.insert( set.toTb(idSet = 0L)) },
                getSpeeches = { idNew-> speechSource.getListSpeech(setId = set.idSet)
                    .map{ item-> item.apply{ setId = idNew} }},
                insertSpeeches = { speechSource.insert(it) },
                copyNested = { ResultData.Success(LongDb(1L)) }
            )
        } else ResultData.Error(ThrowableDS.ErrorTypeSet())
    }.getOrElse { ResultData.Error(ThrowableDS.extract(it)) }

    override fun del(set: Data): ResultData<Data> =
        set.safeUse<SetDb, Long> { item -> dao.delete(item.toTb()).toLong() }

    override fun update(set: Data): ResultData<Data> =
        set.safeUse<SetDb, Long> { setTb -> dao.update(setTb.toTb()).toLong() }
}
