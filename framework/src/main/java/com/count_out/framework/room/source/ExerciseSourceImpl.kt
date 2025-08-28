package com.count_out.framework.room.source

import com.count_out.data.models.SetImplD
import com.count_out.data.models.SpeechKitImplD
import com.count_out.data.models.throwable.ResultSource
import com.count_out.data.models.throwable.ThrowableDS
import com.count_out.data.models.throwable.TypeSource
import com.count_out.data.source.PrimeSource
import com.count_out.data.source.room.ExerciseSource
import com.count_out.data.source.room.SetSource
import com.count_out.framework.room.db.exercise.ExerciseDao
import com.count_out.framework.room.db.exercise.ExerciseTable
import javax.inject.Inject

class ExerciseSourceImpl @Inject constructor(
    private val dao: ExerciseDao,
    private val setSource: SetSource,
    private val speechKitSource: SpeechKitSourceImpl,
): ExerciseSource, PrimeSource() {

    override fun copy(exercise: TypeSource): ResultSource<TypeSource> {
        return try {
            if (exercise is TypeSource.ExerciseT) {
                val speechKitTypeSource = TypeSource.SpeechKitT(
                    exercise.item.speech?.let { it as SpeechKitImplD } ?: SpeechKitImplD())
                speechKitSource.copy(speechKitTypeSource).result { idSpeechKit->
                    if (idSpeechKit is TypeSource.LongT) {
                        dao.add(ExerciseTable(exercise.item,idSpeechKit.item,0L))
                            .let{exerciseId->
                                if (exerciseId == 0L) ResultSource.Error(ThrowableDS.RequestFailed())
                                else if (exercise.item.sets.isNotEmpty()){
                                    var error = false
                                    exercise.item.sets.forEach { set ->
                                        setSource.copy(
                                            TypeSource.SetT(SetImplD(set, exerciseId))).let{
                                                if (it is ResultSource.Error) {
                                                    error = true
                                                    return@forEach
                                                } }
                                    }
                                    if (error) ResultSource.Error(ThrowableDS.RequestFailed())
                                    else ResultSource.Success(TypeSource.LongT(exerciseId))
                                } else setSource.copy(
                                    TypeSource.SetT(SetImplD(exerciseId = exerciseId)))
                            }
                    } else ResultSource.Error(ThrowableDS.NotValidType())
                }
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
    }

    override fun update(exercise: TypeSource): ResultSource<TypeSource> {
        return try {
            if (exercise is TypeSource.ExerciseT) {
                exercise.item.speech?.let {speechKitSource.update(
                    TypeSource.SpeechKitT(SpeechKitImplD(it)))}
                        dao.update(ExerciseTable(exercise.item)).let{exerciseId->
                            if (exerciseId == 0) ResultSource.Error(ThrowableDS.RequestFailed())
                            else ResultSource.Success(TypeSource.IntT(exerciseId))
                        }
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
    }

    override fun changeSequenceExercise(setViewId: TypeSource): ResultSource<TypeSource> {
        return try {
            if (setViewId is TypeSource.SetViewIdT) {
                val from = setViewId.item.from
                val to = setViewId.item.to
                val listExercise = dao.getExerciseRound(setViewId.item.roundId).toMutableList()
                if (from > to) for ( id in to..< from){ listExercise[id].idView = id + 1 }
                else for ( id in (from + 1)..to){ listExercise[id].idView = id - 1}
                listExercise[from].idView = to
                dao.updates(listExercise).let { result->
                    if (result == listExercise.count()) ResultSource.Success(TypeSource.IntT(result))
                    else ResultSource.Error(ThrowableDS.RequestFailed())
                }
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
    }

    override fun del(exercise: TypeSource): ResultSource<TypeSource> {
        return try {
            if (exercise is TypeSource.ExerciseT) {
                var error = false
                exercise.item.speech?.let {
                    speechKitSource.del(TypeSource.SpeechKitT(SpeechKitImplD(it)))}
                if (exercise.item.sets.isNotEmpty()){
                    exercise.item.sets.forEach { set ->
                        setSource.del(TypeSource.SetT(SetImplD( set))).let { result->
                            if (result is ResultSource.Error) {
                                error = true
                                return@forEach
                            } }
                        }
                    }
                if (error)ResultSource.Error(ThrowableDS.RequestFailed())
                else {
                    dao.del(exercise.item.idExercise).let{result->
                        if (result == 0) ResultSource.Error(ThrowableDS.RequestFailed())
                        else ResultSource.Success(TypeSource.IntT(result))
                    }
                }
            } else ResultSource.Error(ThrowableDS.NotValidType())
        } catch (e: Exception) { ResultSource.Error(ThrowableDS.extract(e)) }
    }
}
