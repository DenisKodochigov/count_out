package com.count_out.framework.room.source

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.entity.PartDb
import com.count_out.data.models.entity.RingDb
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.source.room.PartSource
import com.count_out.data.source.room.RingSource
import com.count_out.framework.room.db.part.PartDao
import com.count_out.framework.room.db.part.PartTb.Companion.toTb
import com.count_out.framework.room.db.ring.RingTb.Companion.toTb
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

    override fun copy (part: Data): ResultData<Data> = runCatching {
        if (part is PartDb){
        copyWithDependencies(
            insertMain = { dao.insert(part.toTb().copy(idPart = 0L)) },
            getSpeeches = { idNew-> speechSource.getListSpeech(partId = part.idPart)
                .map{ item-> item.apply{ partId = idNew} }},
            insertSpeeches = { speechSource.insert(it) },
            copyNested = { id -> copyRings(part.rings,id)})}
        else ResultData.Error(ThrowableDS.ErrorTypePart())
    }.getOrElse { ResultData.Error(ThrowableDS.extract(it)) }

    override fun del(part: Data): ResultData<Data> =
        part.safeUse<PartDb, Long> { dao.delete( it.toTb()).toLong() }

    override fun update(part: Data): ResultData<Data> =
        part.safeUse<PartDb, Long> { dao.update(it.toTb()).toLong() }

    //##############################################################################################

    fun copyRings(ringes: List<RingDb>, ownerId: Long): ResultData<Data> =
        if (ringes.isEmpty()) source.insert(RingDb.new(ownerId))
        else ringes.map{ rg->source.insert((rg.toTb()).apply{ this.partId = ownerId}) }[0]
}
