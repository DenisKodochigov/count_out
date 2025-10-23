package com.count_out.framework.room.source

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.entity.LongDb
import com.count_out.data.models.entity.SetDb
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.source.room.SetSource
import com.count_out.framework.room.db.set.SetDao
import com.count_out.framework.room.db.set.SetTb
import com.count_out.framework.room.db.set.SetTb.Companion.toTb
import javax.inject.Inject

class SetSourceImpl @Inject constructor(
    private val speechSource: SpeechSourceImpl,
    private val dao: SetDao
): SetSource, PrimeSource() {
    override fun copy (set: Data): ResultData<Data> = runCatching {
        if (set is SetDb){
            copyWithDependencies(
                original = set.toTb(),
                insertMain = { dao.insert(it.copy(idSet = 0L)) },
                getSpeeches = {speechSource.getListSpeech(setId = set.idSet) },
                insertSpeeches = { speechSource.insert(it) },
                copyNested = { id -> ResultData.Success(LongDb(1))}
            )
        } else ResultData.Error(ThrowableDS.NotValidType())

    }.getOrElse { ResultData.Error(ThrowableDS.extract(it)) }

    override fun del(set: Data): ResultData<Data> =
        set.safeUse<SetDb, Long> { item -> dao.delete(item.toTb()).toLong() }

    override fun update(set: Data): ResultData<Data> =
        set.safeUse<SetDb, Long> { setTb -> dao.update(setTb.toTb()).toLong() }
}
