package com.count_out.framework.room.source

import com.count_out.data.models.SetDb
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ResultSource.Companion.asType
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
    private val speechKitSource: SpeechKitSourceImpl,
): ExerciseSource, PrimeSource() {

    override fun copy(exercise: TypeSource): ResultSource<TypeSource> =
        (exercise as? TypeSource.ExerciseT)?.let { ex ->
            speechKitSource.insert(ex.item.speechId ?: 0).asType<TypeSource.LongT>()
            .flatMap { idSpeechKit ->
                val tempEx = (ex.item as ExerciseTb).apply {
                    this.speechId = idSpeechKit.item
                    this.idExercise = 0L }
                dao.insert(tempEx).result()
            }
            .flatMap { ownerId->
                if (ownerId.item == 0L) ResultSource.Error(ThrowableDS.RequestFailed())
                else copySets(ex.item.sets, ownerId)
            }
        } ?: ResultSource.Error(ThrowableDS.NotValidType())

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


}
