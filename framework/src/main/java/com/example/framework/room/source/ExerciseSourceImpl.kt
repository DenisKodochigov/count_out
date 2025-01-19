package com.example.framework.room.source

import android.util.Log
import com.example.data.entity.ExerciseImpl
import com.example.data.entity.SetImpl
import com.example.data.source.room.ExerciseSource
import com.example.domain.entity.Exercise
import com.example.domain.entity.enums.DistanceUnit
import com.example.domain.entity.enums.Goal
import com.example.domain.entity.enums.TimeUnit
import com.example.domain.entity.enums.WeightUnit
import com.example.domain.entity.enums.Zone
import com.example.framework.room.db.exercise.ExerciseDao
import com.example.framework.room.db.exercise.ExerciseTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseSourceImpl @Inject constructor(
    private val setSource: SetSourceImpl,
    private val speechKitSource: SpeechKitSourceImpl,
    private val dao: ExerciseDao
): ExerciseSource {
    override fun gets(): Flow<List<Exercise>> = dao.gets().map { list-> list.map { it.toExercise() } }

    override fun get(id: Long): Flow<ExerciseImpl> = dao.get(id).map { it.toExercise() }

    override fun getForRound(id: Long): Flow<List<ExerciseImpl>> =
        dao.getForRound(id).map { list-> list.map { it.toExercise() }}

    override fun getForRing(id: Long): Flow<List<ExerciseImpl>> =
        dao.getForRing(id).map { list-> list.map { it.toExercise() }}

    override fun getFilter(list: List<Long>): Flow<List<ExerciseImpl>> =
        dao.getFilter(list).map { lst-> lst.map { it.toExercise() }}

    override fun addCopy(exercise: ExerciseImpl): Flow<List<ExerciseImpl>> {
        if (exercise.roundId > 0L || exercise.ringId > 0L) {
            val idSpeechKit = (speechKitSource.add(exercise.speech) as StateFlow ).value.idSpeechKit
            val idExercise = (dao.add(toExerciseTable(exercise.copy(speechId = idSpeechKit)))
                .map { it.toExercise() } as StateFlow).value.idExercise
            if (exercise.sets.isNotEmpty()){
                exercise.sets.forEach { set-> setSource.addCopy(set.copy(exerciseId = idExercise)) }
            } else {
                setSource.addCopy( newSetImpl(exerciseId = idExercise))
            }
        } else (Log.d("KDS"," Не определен roundID or RING_ID"))
        return if (exercise.roundId > 0) getForRound(exercise.roundId) else getForRing(exercise.ringId)
    }

    override fun update(exercise: ExerciseImpl): Flow<ExerciseImpl> =
        dao.update(toExerciseTable(exercise).copy(idExercise = exercise.idExercise)).map { it.toExercise() }

    override fun setActivityIntoExercise(exerciseId: Long, activityId: Long): Flow<ExerciseImpl> =
        dao.setActivity(exerciseId, activityId).map { it.toExercise() }

    override fun del(exercise: ExerciseImpl) {
        exercise.speech?.let { speechKitSource.del(it) }
        dao.del(exercise.idExercise) }

    private fun toExerciseTable(exercise: ExerciseImpl) = ExerciseTable(
        roundId = exercise.roundId,
        ringId = exercise.ringId,
        activityId = exercise.activityId,
        idView = exercise.idView,
        speechId = exercise.speechId,
    )
    private fun newSetImpl(exerciseId: Long) = SetImpl(
        exerciseId = exerciseId,
        idSet = 0,
        name = "Set 1",
        speechId = 0,
        speech = null,
        goal = Goal.Count,
        weight = 1,
        weightU = WeightUnit.KG,
        distance = 1.0,
        distanceU = DistanceUnit.KM,
        duration = 1,
        durationU = TimeUnit.M,
        reps = 10,
        intensity = Zone.Medium,
        intervalReps = 1.0,
        intervalDown = 0,
        groupCount = "",
        timeRest = 1,
        timeRestU = TimeUnit.M
    )
}