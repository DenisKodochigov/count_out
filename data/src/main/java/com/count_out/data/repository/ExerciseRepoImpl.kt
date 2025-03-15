package com.count_out.data.repository

import com.count_out.data.models.ExerciseImpl
import com.count_out.data.source.room.ExerciseSource
import com.count_out.domain.entity.DataForChangeSequence
import com.count_out.domain.entity.workout.Exercise
import com.count_out.domain.repository.trainings.ExerciseRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class ExerciseRepoImpl @Inject constructor(private val exerciseSource: ExerciseSource): ExerciseRepo {
    override fun get(exercise: Exercise): Flow<Exercise> = exerciseSource.get(exercise as ExerciseImpl)
    override fun del(exercise: Exercise): Flow<List<Exercise>> {
        exerciseSource.del(exercise as ExerciseImpl)
        return getExercise(exercise)
    }

    override fun copy(exercise: Exercise): Flow<List<Exercise>> {
        exerciseSource.copy(exercise as ExerciseImpl)
        return getExercise(exercise)
    }
    override fun update(exercise: Exercise): Flow<Exercise> {
        exerciseSource.update(exercise as ExerciseImpl)
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