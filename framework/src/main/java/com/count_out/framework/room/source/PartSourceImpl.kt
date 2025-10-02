package com.count_out.framework.room.source

import com.count_out.data.models.RingDb
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ResultSource.Companion.asType
import com.count_out.data.models.throwable.ResultSource.Companion.flatMap
import com.count_out.data.models.throwable.ResultSource.Success
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.PartSource
import com.count_out.data.source.room.RingSource
import com.count_out.framework.result
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
    private val speechKitSource: SpeechKitSourceImpl,
): PartSource, PrimeSource() {

    override fun copy(part: TypeSource): ResultSource<TypeSource> =
        (part as? TypeSource.PartT)?.let { pr ->
        speechKitSource.insert(pr.item.speechId ?: 0).asType<TypeSource.LongT>()
        .flatMap { idSpeechKit ->
            val obj = (pr.item as PartTb).apply{this.speechId = idSpeechKit.item; this.idPart = 0L }
            dao.insert(obj).result() }
        .flatMap { ownerId->
            if (ownerId.item == 0L) ResultSource.Error(ThrowableDS.RequestFailed())
            else copyRings(pr.item.rings, ownerId) }
        } ?: ResultSource.Error(ThrowableDS.NotValidType())

    override fun del(part: TypeSource): ResultSource<TypeSource> =
        part.use { dao.delete( it).toLong() }

    override fun update(part: TypeSource): ResultSource<TypeSource> =
        part.use { dao.update(it).toLong() }

    //##############################################################################################
    inline fun TypeSource.use(crossinline block: (PartTb) -> Long): ResultSource<TypeSource> =
        if (this is TypeSource.PartT) {
            try { block(this.item as PartTb).result() }
            catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
        } else ResultSource.Error(ThrowableDS.NotValidType())

    fun copyRings(ringes: List<RingDb>, ownerId: TypeSource.LongT): ResultSource<TypeSource> =
        if (ringes.isEmpty()) { Success(TypeSource.IntT(0)) }
        else {
            ringes.map {ex-> source.copy(TypeSource.RingT(
                (ex as RingTb).apply{ this.partId = ownerId.item})) }
                .firstOrNull {it is ResultSource.Error}
                ?: Success(TypeSource.IntT(ringes.size))
        }
}
