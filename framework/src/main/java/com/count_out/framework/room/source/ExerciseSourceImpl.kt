package com.count_out.framework.room.source

import com.count_out.data.models.Data
import com.count_out.data.models.ExerciseDb
import com.count_out.data.models.SetDb
import com.count_out.data.models.SetIdViewDb
import com.count_out.data.models.throwable.ResultData
import com.count_out.data.models.throwable.ResultData.Companion.flatMap
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.types_data.LongDb
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.ExerciseSource
import com.count_out.data.source.room.SetSource
import com.count_out.framework.room.db.exercise.ExerciseDao
import com.count_out.framework.room.db.exercise.ExerciseTb
import com.count_out.framework.room.db.set.SetTb
import javax.inject.Inject

class ExerciseSourceImpl @Inject constructor(
    private val dao: ExerciseDao,
    private val setSource: SetSource,
    private val speechSource: SpeechSourceImpl,
): ExerciseSource, PrimeSource() {

    override fun copy(exercise: Data): ResultData<Data> =
        exercise.safeUse<ExerciseTb, ResultData<Data>> { exerciseTb->
            var idNew = LongDb(0L)
            dao.insert(exerciseTb.copy(idExercise = 0L)).result()
                .flatMap { ownerId->
                    idNew = ownerId as LongDb
                    val listSpeech = speechSource.getListSpeech(exerciseId = exerciseTb.idExercise)
                        .map { it.apply { exerciseId = ownerId.item } }
                    if (speechSource.insert(listSpeech).count() == listSpeech.count())
                        ResultData.Success(LongDb(listSpeech.count().toLong()))
                    else ResultData.Error(ThrowableDS.RequestFailed()) }
                .flatMap { copySets(exerciseTb.sets, ownerId = idNew) }
            }

    fun copySets(sets: List<SetDb>, ownerId: Data): ResultData<Data> =
        if (sets.isEmpty()) { ResultData.Success(LongDb(0L)) }
        else {
            sets.map {set-> setSource.copy(
                (set as SetTb).copy(idSet = 0L, exerciseId = (ownerId as LongDb).item)) }
                .firstOrNull {it is ResultData.Error}
                ?: ResultData.Success(LongDb(sets.size.toLong()))
        }

    override fun update(exercise: Data): ResultData<Data> =
        exercise.safeUse<ExerciseTb, Long>{ item -> dao.update(item).toLong() }

    override fun changeSequenceExercise(setViewId: Data): ResultData<Data> {
        return try {
            if (setViewId is SetIdViewDb) {
                val from = setViewId.from
                val to = setViewId.to
                val listExercise = dao.getExerciseRound(setViewId.ringId).toMutableList()
                if (from > to) for ( id in to..< from){ listExercise[id].idView = id + 1 }
                else for ( id in (from + 1)..to){ listExercise[id].idView = id - 1}
                listExercise[from].idView = to
                dao.update(listExercise).let { result->
                    if (result == listExercise.count()) ResultData.Success(LongDb(result.toLong()))
                    else ResultData.Error(ThrowableDS.RequestFailed())
                }
            } else ResultData.Error(ThrowableDS.NotValidType())
        } catch (e: Exception) { ResultData.Error(ThrowableDS.extract(e)) }
    }

    override fun del(exercise: Data): ResultData<Data> =
        exercise.safeUse<ExerciseTb, Long>{ item -> dao.delete(item).toLong() }
}
