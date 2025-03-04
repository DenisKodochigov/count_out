package com.count_out.framework.room.source

import android.util.Log
import com.count_out.data.models.ActionWithSetImpl
import com.count_out.data.models.ExerciseImpl
import com.count_out.data.models.ParameterImpl
import com.count_out.data.models.SetImpl
import com.count_out.data.source.room.ExerciseSource
import com.count_out.data.source.room.SetSource
import com.count_out.data.source.room.SpeechKitSource
import com.count_out.entity.entity.workout.Exercise
import com.count_out.entity.enums.Goal
import com.count_out.entity.enums.Units
import com.count_out.entity.enums.Zone
import com.count_out.framework.room.db.exercise.ExerciseDao
import com.count_out.framework.room.db.exercise.ExerciseTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseSourceImpl @Inject constructor(
    private val dao: ExerciseDao,
    private val setSource: SetSource,
    private val speechKitSource: SpeechKitSource,
): ExerciseSource {
    override fun gets(): Flow<List<Exercise>> = dao.gets().map { list-> list.map { it.toExercise() } }

    override fun get(id: Long): Flow<ExerciseImpl> = dao.get(id).map { it.toExercise() }

    override fun getForRound(id: Long): Flow<List<ExerciseImpl>> =
        dao.getForRound(id).map { list-> list.map { it.toExercise() }}

    override fun getForRing(id: Long): Flow<List<ExerciseImpl>> =
        dao.getForRing(id).map { list-> list.map { it.toExercise() }}

    override fun getFilter(list: List<Long>): Flow<List<ExerciseImpl>> =
        dao.getFilter(list).map { lst-> lst.map { it.toExercise() }}

    override fun copy(exercise: ExerciseImpl): Flow<List<ExerciseImpl>> {
        val result  = if (exercise.roundId > 0L || exercise.ringId > 0L) {
            val idSpeechKit = exercise.speech?.let {
                (speechKitSource.copy(it) as StateFlow ).value.idSpeechKit } ?: 0
            val idExercise = dao.add(toExerciseTable(exercise.copy(speechId = idSpeechKit)))
            if (exercise.sets.isNotEmpty()){
                exercise.sets.forEach { set-> setSource.copy(
                    ActionWithSetImpl(idExercise, set.copy(exerciseId = idExercise))) }
            } else {
                setSource.copy( ActionWithSetImpl(idExercise, newSetImpl(exerciseId = idExercise)) )
            }
            if (exercise.roundId > 0) getForRound(exercise.roundId) else getForRing(exercise.ringId)
        } else {
            Log.d("KDS", " Не определен roundID or RING_ID")
            flow { emit( emptyList()) }
        }
        return result
    }

    override fun add(roundId: Long, ringId: Long): Flow<List<ExerciseImpl>> {
        val result  = if (roundId > 0L || ringId > 0L) {
            val idExercise = dao.add(
                ExerciseTable(speechId = (speechKitSource.add() as StateFlow ).value.idSpeechKit))
            setSource.add(ActionWithSetImpl(idExercise, newSetImpl(exerciseId = idExercise)))
            if (roundId > 0) getForRound(roundId) else getForRing(ringId)
        } else {
            Log.d("KDS", " Не определен roundID or RING_ID")
            flow { emit( emptyList()) }
        }
        return result
    }
    override fun update(exercise: ExerciseImpl): Flow<ExerciseImpl> {
        dao.update(toExerciseTable(exercise).copy(idExercise = exercise.idExercise))
        return get(exercise.idExercise)
    }

    override fun setActivityIntoExercise(exerciseId: Long, activityId: Long): Flow<ExerciseImpl> {
        dao.setActivity(exerciseId, activityId)
        return get(exerciseId)
    }

    override fun del(exercise: ExerciseImpl): Flow<List<ExerciseImpl>> {
        exercise.speech?.let { speechKitSource.del(it) }
        dao.del(exercise.idExercise)
        return if (exercise.roundId > 0) getForRound(exercise.roundId) else getForRing(exercise.ringId)
    }

    private fun toExerciseTable(exercise: ExerciseImpl) = ExerciseTable(
        roundId = exercise.roundId,
        ringId = exercise.ringId,
        activityId = exercise.activityId,
        idView = exercise.idView,
        speechId = exercise.speechId,
    )
    private fun newSetImpl(exerciseId: Long) = SetImpl(
        idSet = 0,
        exerciseId = exerciseId,
        name = "Set 1",
        speechId = 0,
        speech = null,
        goal = Goal.Count,
        weight = ParameterImpl(value = 1.0, unit = Units.KG),
        distance = ParameterImpl(value = 1.0, unit = Units.MT),
        duration = ParameterImpl(value = 1.0, unit = Units.S),
        reps = 10,
        intensity = Zone.Medium,
        intervalReps = 1.0,
        intervalDown = 0,
        groupCount = "",
        rest = ParameterImpl(value = 1.0, unit = Units.S),
    )
}