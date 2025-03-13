package com.count_out.framework.room.source

import com.count_out.data.models.ExerciseImpl
import com.count_out.data.models.ParameterImpl
import com.count_out.data.models.SetImpl
import com.count_out.data.models.SpeechKitImpl
import com.count_out.data.source.room.ExerciseSource
import com.count_out.data.source.room.SetSource
import com.count_out.data.source.room.SpeechKitSource
import com.count_out.domain.entity.enums.Goal
import com.count_out.domain.entity.enums.Units
import com.count_out.domain.entity.enums.Zone
import com.count_out.framework.room.db.exercise.ExerciseDao
import com.count_out.framework.room.db.exercise.ExerciseTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseSourceImpl @Inject constructor(
    private val dao: ExerciseDao,
    private val setSource: SetSource,
    private val speechKitSource: SpeechKitSource,
): ExerciseSource {

    override fun get(exercise: ExerciseImpl): Flow<ExerciseImpl> =
        dao.get(exercise.idExercise).map { it.toExercise() }

    override fun getForRound(id: Long): Flow<List<ExerciseImpl>> =
        dao.getForRound(id).map { list-> list.map { it.toExercise() }}

    override fun getForRing(id: Long): Flow<List<ExerciseImpl>> =
        dao.getForRing(id).map { list-> list.map { it.toExercise() }}

    override fun getFilter(list: List<Long>): Flow<List<ExerciseImpl>> =
        dao.getFilter(list).map { lst-> lst.map { it.toExercise() }}

    override fun copy(exercise: ExerciseImpl): Long {
        val speechId = speechKitSource.copy(exercise.speech?.let{ it as SpeechKitImpl } ?: SpeechKitImpl() )
        val id = dao.add(toExerciseTable(exercise.copy(speechId = speechId)))
        if (exercise.sets.isNotEmpty()){
            exercise.sets.forEach { set-> setSource.copy((set as SetImpl).copy(exerciseId = id)) }
        } else { setSource.copy( newSetImpl(exerciseId = id) ) }
        return id
    }

    override fun update(exercise: ExerciseImpl) {
        exercise.speech?.let {  speechKitSource.update(it) }
        if (exercise.sets.isNotEmpty()){
            exercise.sets.forEach { set-> setSource.update((set as SetImpl)) }
        }
        dao.update(toExerciseTable(exercise).copy(idExercise = exercise.idExercise))
    }

    override fun del(exercise: ExerciseImpl) {
        exercise.speech?.let { speechKitSource.del(it as SpeechKitImpl) }
        if (exercise.sets.isNotEmpty()){
            exercise.sets.forEach { set-> setSource.del(set as SetImpl) } }
        dao.del(exercise.idExercise)
    }

    private fun toExerciseTable(exercise: ExerciseImpl) = ExerciseTable(
//        idExercise = exercise.idExercise,
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