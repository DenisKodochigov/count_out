package com.count_out.framework.room.source

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.entity.ExerciseDb
import com.count_out.data.models.entity.LongDb
import com.count_out.data.models.throwable.ThrowableDS
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

    override fun insert (ring: Data): ResultData<Data> = runCatching {
        copyWithDependencies(
            original = (ring as RingTb),
            originalId = ring.idRing,
            insertMain = { dao.insert(it.copy(idRing = 0L)) },
            getSpeeches = { oldId -> speechSource.getListSpeech(ringId = oldId) },
            insertSpeeches = { speechSource.insert(it) },
            copyNested = { id -> copyExercises(ring.exercises,id)}
        )
    }.getOrElse { ResultData.Error(ThrowableDS.extract(it)) }

    override fun del(ring: Data): ResultData<Data> =
        ring.safeUse<RingTb, Long> { dao.delete( it).toLong() }

    override fun update(ring: Data): ResultData<Data> =
        ring.safeUse<RingTb, Long> { dao.update(it).toLong() }

//##############################################################################################
    fun copyExercises(exercises: List<ExerciseDb>, id: Long): ResultData<LongDb> =
        if (exercises.isEmpty()) { ResultData.Success(LongDb(0L)) }
        else {
            exercises.map{ex-> source.copy(
                (ex as ExerciseTb).apply{this.ringId = id}) }
                ResultData.Success(LongDb(exercises.size.toLong()))
        }
}
