package com.count_out.framework.room.source

import com.count_out.data.models.Data
import com.count_out.data.models.ExerciseDb
import com.count_out.data.models.throwable.ResultData
import com.count_out.data.models.throwable.ResultData.Companion.flatMap
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.types_data.LongDb
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.ExerciseSource
import com.count_out.data.source.room.RingSource
import com.count_out.framework.room.db.exercise.ExerciseTb
import com.count_out.framework.room.db.ring.RingDao
import com.count_out.framework.room.db.ring.RingTb
import javax.inject.Inject

/**
 * Добавляем раунд только когда создаем новый тренировочный план. Поэтому эта функция не появляется в Repo
 * Удаляем раунд только когда удалякм тренировочный план. Поэтому эта функция не появляется в Repo
 */
class RingSourceImpl @Inject constructor(
    private val dao: RingDao,
    private val source: ExerciseSource,
    private val speechSource: SpeechSourceImpl,
): RingSource, PrimeSource() {

    override fun copy(ring: Data): ResultData<Data> =
        ring.safeUse<RingTb, ResultData<Data>> { ringTb->
            var idNew = LongDb(0L)
            dao.insert(ringTb.copy(idRing = 0L)).result()
                .flatMap { ownerId ->
                    idNew = ownerId as LongDb
                    val listSpeech = speechSource.getListSpeech( ringId = ringTb.idRing)
                        .map { it.apply { ringId = ownerId.item } }
                    if (speechSource.insert(listSpeech).count() == listSpeech.count())
                        ResultData.Success(LongDb(listSpeech.count().toLong()))
                    else ResultData.Error(ThrowableDS.RequestFailed()) }
                .flatMap { copyExercises(ringTb.exercises, ownerId = idNew) }
        }

    override fun del(ring: Data): ResultData<Data> =
        ring.safeUse<RingTb, Long> { dao.delete( it).toLong() }

    override fun update(ring: Data): ResultData<Data> =
        ring.safeUse<RingTb, Long> { dao.update(it).toLong() }

//##############################################################################################
    fun copyExercises(exercises: List<ExerciseDb>, ownerId: LongDb): ResultData<Data> =
        if (exercises.isEmpty()) { ResultData.Success(LongDb(0L)) }
        else {
            exercises.map {ex-> source.copy(
                (ex as ExerciseTb).apply{ this.ringId = ownerId.item}) }
                .firstOrNull {it is ResultData.Error}
                ?: ResultData.Success(LongDb(exercises.size.toLong()))
        }

}
