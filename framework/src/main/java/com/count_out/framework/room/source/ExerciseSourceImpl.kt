package com.count_out.framework.room.source

import com.count_out.data.models.SetDb
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ResultSource.Companion.flatMap
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.ExerciseSource
import com.count_out.data.source.room.SetSource
import com.count_out.framework.result
import com.count_out.framework.room.db.exercise.ExerciseDao
import com.count_out.framework.room.db.exercise.ExerciseTb
import com.count_out.framework.room.db.set.SetTb
import javax.inject.Inject

class ExerciseSourceImpl @Inject constructor(
    private val dao: ExerciseDao,
    private val setSource: SetSource,
    private val speechSource: SpeechSourceImpl,
): ExerciseSource, PrimeSource() {

    override fun copy(exercise: TypeSource): ResultSource<TypeSource> =
        exercise.useResult { exerciseTb->
            dao.insert(exerciseTb.copy(idExercise = 0L)).result()
                .flatMap { ownerId->
                    val listSpeech = speechSource.getListSpeech(exerciseId = exerciseTb.idExercise)
                        .map { it.apply { exerciseId = ownerId.item } }
                    if (speechSource.insert(listSpeech).count() == listSpeech.count())
                        ResultSource.Success(TypeSource.IntT(listSpeech.count()))
                    else ResultSource.Error(ThrowableDS.RequestFailed())
                }
            }


    fun copySets(sets: List<SetDb>, ownerId: TypeSource.LongT): ResultSource<TypeSource> =
        if (sets.isEmpty()) { ResultSource.Success(TypeSource.IntT(0)) }
        else {
            sets.map {set-> setSource.copy(TypeSource.SetT(
                (set as SetTb).copy(idSet = 0L, exerciseId = ownerId.item))) }
                .firstOrNull {it is ResultSource.Error}
                ?: ResultSource.Success(TypeSource.IntT(sets.size))
        }

    override fun update(exercise: TypeSource): ResultSource<TypeSource> =
        exercise.use{ item -> dao.update(item).toLong() }

    override fun changeSequenceExercise(setViewId: TypeSource): ResultSource<TypeSource> {
        return try {
            if (setViewId is TypeSource.SetViewIdT) {
                val from = setViewId.item.from
                val to = setViewId.item.to
                val listExercise = dao.getExerciseRound(setViewId.item.ringId).toMutableList()
                if (from > to) for ( id in to..< from){ listExercise[id].idView = id + 1 }
                else for ( id in (from + 1)..to){ listExercise[id].idView = id - 1}
                listExercise[from].idView = to
                dao.update(listExercise).let { result->
                    if (result == listExercise.count()) ResultSource.Success(TypeSource.IntT(result))
                    else ResultSource.Error(ThrowableDS.RequestFailed())
                }
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
    }

    override fun del(exercise: TypeSource): ResultSource<TypeSource> =
        exercise.use{ item -> dao.delete(item).toLong() }

    inline fun TypeSource.use(crossinline block: (ExerciseTb) -> Long): ResultSource<TypeSource> =
        if (this is TypeSource.ExerciseT) {
            try { block(this.item as ExerciseTb).result() }
            catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
        } else ResultSource.Error(ThrowableDS.NotValidType())

    inline fun TypeSource.useResult(crossinline block: (ExerciseTb) -> ResultSource<TypeSource>): ResultSource<TypeSource> =
        if (this is TypeSource.ExerciseT) {
            try { block(this.item as ExerciseTb) }
            catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
        } else ResultSource.Error(ThrowableDS.NotValidType())

}
