package com.count_out.framework.room.source

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.entity.ExerciseDb
import com.count_out.data.models.entity.LongDb
import com.count_out.data.models.entity.RingDb
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.source.room.ExerciseSource
import com.count_out.data.source.room.RingSource
import com.count_out.framework.room.db.exercise.ExerciseTb.Companion.toTb
import com.count_out.framework.room.db.ring.RingDao
import com.count_out.framework.room.db.ring.RingTb.Companion.toTb
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

    override fun insert (ring: Data): ResultData<Data> = runCatching {
        if (ring is RingDb){
            copyWithDependencies(
                original = (ring.toTb()),
                insertMain = { dao.insert(it.copy(idRing = 0L)) },
                getSpeeches = { speechSource.getListSpeech(ringId = ring.idRing) },
                insertSpeeches = { speechSource.insert(it) },
                copyNested = { id -> copyExercises(ring.exercises,id)}
            )
        } else ResultData.Error(ThrowableDS.NotValidType())
    }.getOrElse { ResultData.Error(ThrowableDS.extract(it)) }

    override fun del(ring: Data): ResultData<Data> =
        ring.safeUse<RingDb, Long> { dao.delete( it.toTb()).toLong() }

    override fun update(ring: Data): ResultData<Data> =
        ring.safeUse<RingDb, Long> { dao.update(it.toTb()).toLong() }

//##############################################################################################
    fun copyExercises(exercises: List<ExerciseDb>, id: Long): ResultData<LongDb> =
        if (exercises.isEmpty()) { ResultData.Success(LongDb(0L)) }
        else {
            exercises.map{ex-> source.copy(
                (ex.toTb()).apply{this.ringId = id}) }
//                (ex.convert()).apply{this.ringId = id}) }
                ResultData.Success(LongDb(exercises.size.toLong()))
        }
}
