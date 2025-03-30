package com.count_out.data.repository

import com.count_out.data.models.ExerciseImplD
import com.count_out.data.source.room.ExerciseSource
import com.count_out.domain.entity.DataForChangeSequence
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.repository.trainings.ExerciseRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class ExerciseRepoImpl @Inject constructor(private val exerciseSource: ExerciseSource): ExerciseRepo {
    override fun get(exercise: Exercise): Flow<Exercise> = exerciseSource.get(ExerciseImplD(exercise)).filterNotNull()
    override fun del(exercise: Exercise): Flow<List<Exercise>> {
        exerciseSource.del(ExerciseImplD(exercise))
        return getExercise(exercise)
    }

    override fun copy(exercise: Exercise): Flow<List<Exercise>> {
        exerciseSource.copy(ExerciseImplD(exercise))
        return getExercise(exercise)
    }
    override fun update(exercise: Exercise): Flow<Exercise> {
        exerciseSource.update(ExerciseImplD(exercise))
        return get(exercise)
    }
    override fun getForRound(id: Long): Flow<List<Exercise>> = exerciseSource.getForRound(id)
    override fun getForRing(id: Long): Flow<List<Exercise>> = exerciseSource.getForRing(id)
    override fun getFilter(list: List<Long>): Flow<List<Exercise>> = exerciseSource.getFilter(list)

    override fun changeSequenceExercise(item: DataForChangeSequence): Flow<List<Exercise>> {
        TODO("Not yet implemented")
    }
    fun getExercise(exercise: Exercise): Flow<List<Exercise>>{
        return if (exercise.roundId != 0L) getForRound(exercise.roundId)
        else if (exercise.ringId != 0L) getForRound(exercise.ringId)
        else flow { emit(emptyList()) }
    }
}