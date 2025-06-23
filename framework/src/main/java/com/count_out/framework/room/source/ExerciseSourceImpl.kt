package com.count_out.framework.room.source

import com.count_out.data.models.ExerciseImplD
import com.count_out.data.models.ParameterImpl
import com.count_out.data.models.SetImplD
import com.count_out.data.models.SpeechKitImplD
import com.count_out.data.source.room.ExerciseSource
import com.count_out.data.source.room.SetSource
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
    private val speechKitSource: SpeechKitSourceImpl,
): ExerciseSource {

    override fun get(exercise: ExerciseImplD): Flow<ExerciseImplD?> =
        dao.get(exercise.idExercise).map { it?.toExercise() }

    override fun getForRound(id: Long): Flow<List<ExerciseImplD>> =
        dao.getForRound(id).map { list-> list.map { it.toExercise() }}

    override fun getForRing(id: Long): Flow<List<ExerciseImplD>> =
        dao.getForRing(id).map { list-> list.map { it.toExercise() }}

    override fun getFilter(list: List<Long>): Flow<List<ExerciseImplD>> =
        dao.getFilter(list).map { lst-> lst.map { it.toExercise() }}

    override fun copy(exercise: ExerciseImplD): Long {
        val speechId = speechKitSource.copyValue(exercise.speech?.let{ it as SpeechKitImplD } ?: SpeechKitImplD() ) ?: 0L
        val id = dao.add(toExerciseTable(exercise, speechId))
        if (exercise.sets.isNotEmpty()){
            exercise.sets.forEach { set-> setSource.copy((set as SetImplD).copy(exerciseId = id)) }
        } else { setSource.copy( newSetImpl(exerciseId = id) ) }
        return id
    }

    override fun update(exercise: ExerciseImplD) {
        exercise.speech?.let {  speechKitSource.update(SpeechKitImplD(it)) }
        if (exercise.sets.isNotEmpty()){
            exercise.sets.forEach { set-> setSource.update((set as SetImplD)) }
        }
        dao.update(toExerciseTable(exercise, exercise.speechId, exercise.idExercise))
    }

    override fun del(exercise: ExerciseImplD) {
        exercise.speech?.let { speechKitSource.del(it as SpeechKitImplD) }
        if (exercise.sets.isNotEmpty()){
            exercise.sets.forEach { set-> setSource.del(set as SetImplD) } }
        dao.del(exercise.idExercise)
    }

    private fun toExerciseTable(exercise: ExerciseImplD, speechId: Long = 0L, idExercise: Long = 0L) =
        ExerciseTable(
            idExercise = idExercise,
            roundId = exercise.roundId,
            ringId = exercise.ringId,
            activityId = exercise.activityId,
            idView = exercise.idView,
            speechId = speechId,
        )
    private fun newSetImpl(exerciseId: Long) = SetImplD(
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