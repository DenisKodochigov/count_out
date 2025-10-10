package com.count_out.framework.room.source

import com.count_out.data.models.Data
import com.count_out.data.models.RingDb
import com.count_out.data.models.throwable.ResultData
import com.count_out.data.models.throwable.ResultData.Companion.flatMap
import com.count_out.data.models.throwable.ResultData.Success
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.types_data.LongDb
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.PartSource
import com.count_out.data.source.room.RingSource
import com.count_out.framework.room.db.part.PartDao
import com.count_out.framework.room.db.part.PartTb
import com.count_out.framework.room.db.ring.RingTb
import javax.inject.Inject

/**
 * Добавляем раунд только когда создаем новый тренировочный план. Поэтому эта функция не появляется в Repo
 * Удаляем раунд только когда удалякм тренировочный план. Поэтому эта функция не появляется в Repo
 */
class PartSourceImpl @Inject constructor(
    private val dao: PartDao,
    private val source: RingSource,
    private val speechSource: SpeechSourceImpl,
): PartSource, PrimeSource() {

    override fun copy(part: Data): ResultData<Data> =
        part.safeUse<PartTb, ResultData<Data>> { partTb->
            var idNew = LongDb(0L)
            dao.insert(partTb.copy(idPart = 0L)).result()
                .flatMap { ownerId->
                    idNew = ownerId as LongDb
                    val listSpeech = speechSource.getListSpeech(partId = partTb.idPart)
                        .map { it.apply { partId = ownerId.item } }
                    if (speechSource.insert(listSpeech).count() == listSpeech.count())
                        ResultData.Success(LongDb(listSpeech.count().toLong()))
                    else ResultData.Error(ThrowableDS.RequestFailed()) }
                .flatMap { copyRings(partTb.rings, ownerId = idNew) }
        }

    override fun del(part: Data): ResultData<Data> =
        part.safeUse<PartTb, Long> { dao.delete( it).toLong() }

    override fun update(part: Data): ResultData<Data> =
        part.safeUse<PartTb, Long> { dao.update(it).toLong() }

    //##############################################################################################

    fun copyRings(ringes: List<RingDb>, ownerId: LongDb): ResultData<Data> =
        if (ringes.isEmpty()) { Success(LongDb(0)) }
        else {
            ringes.map {ex-> source.copy((ex as RingTb).apply{ this.partId = ownerId.item}) }
                .firstOrNull {it is ResultData.Error}
                ?: Success(LongDb(ringes.size.toLong()))
        }
}
