package com.count_out.framework.room.source

import com.count_out.data.models.Data
import com.count_out.data.models.ResultData
import com.count_out.data.models.entity.ExerciseDb
import com.count_out.data.models.entity.LongDb
import com.count_out.data.models.entity.RingDb
import com.count_out.data.models.entity.SetViewIdDb
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
                insertMain = { dao.insert(ring.toTb(idRing = 0L)) },
                getSpeeches = { idNew ->  speechSource.getListSpeech(ringId = ring.idRing)
                    .map{ item-> item.apply{ ringId = idNew} }},
                insertSpeeches = { speechSource.insert(it) },
                copyNested = { id -> copyExercises(ring.exercises,id)}
            )
        } else ResultData.Error(ThrowableDS.NotValidType())
    }.getOrElse { ResultData.Error(ThrowableDS.extract(it)) }

    override fun del(ring: Data): ResultData<Data> =
        ring.safeUse<RingDb, Long> { dao.delete( it.toTb()).toLong() }

    override fun update(ring: Data): ResultData<Data> =
        ring.safeUse<RingDb, Long> { dao.update(it.toTb()).toLong() }

    override fun changeSequence(setViewId: Data): ResultData<Data> {
        return try {
            if (setViewId is SetViewIdDb) {
                val from = setViewId.from
                val to = setViewId.to
                val listRing = dao.getRingInPart(setViewId.idOwner).toMutableList()
                if (from > to) for ( id in to..< from){ listRing[id].idView = (id + 1) }
                else for ( id in (from + 1)..to){ listRing[id].idView = (id - 1)}
                listRing[from].idView = to
                dao.update(listRing).let { result->
                    if (result == listRing.count()) ResultData.Success(LongDb(result.toLong()))
                    else ResultData.Error(ThrowableDS.ErrorExercises())
                }
            } else ResultData.Error(ThrowableDS.ErrorTypeSetIdView())
        } catch (e: Exception) { ResultData.Error(ThrowableDS.extract(e)) }
    }
//##############################################################################################
    fun copyExercises(exercises: List<ExerciseDb>, ownerId: Long): ResultData<Data> =
        if (exercises.isEmpty()) source.insert( ExerciseDb.new(ownerId))
        else exercises.map { exercise->
            source.insert(exercise.toTb(idExercise = 0L, ringId = ownerId)) }[0]

}
