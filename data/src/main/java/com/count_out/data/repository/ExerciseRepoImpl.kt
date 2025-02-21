package com.count_out.data.repository

import com.count_out.data.entity.ExerciseImpl
import com.count_out.data.source.room.ExerciseSource
import com.count_out.domain.entity.ActionWithActivity
import com.count_out.domain.entity.DataForChangeSequence
import com.count_out.domain.entity.Exercise
import com.count_out.domain.repository.trainings.ExerciseRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseRepoImpl @Inject constructor(private val exerciseSource: ExerciseSource): ExerciseRepo {
    override fun get(id: Long): Flow<Exercise> = exerciseSource.get(id)
    override fun gets(): Flow<List<Exercise>> = exerciseSource.gets()
    override fun del(exercise: Exercise): Flow<List<Exercise>> = exerciseSource.del(exercise as ExerciseImpl)
    override fun add(exercise: Exercise): Flow<List<Exercise>> = exerciseSource.add()
    override fun copy(exercise: Exercise): Flow<List<Exercise>> = exerciseSource.copy(exercise as ExerciseImpl)
    override fun update(exercise: Exercise): Flow<Exercise> = exerciseSource.update(exercise as ExerciseImpl)
    override fun getForRound(id: Long): Flow<List<Exercise>> = exerciseSource.getForRound(id)
    override fun getForRing(id: Long): Flow<List<Exercise>> = exerciseSource.getForRing(id)
    override fun getFilter(list: List<Long>): Flow<List<Exercise>> = exerciseSource.getFilter(list)
    override fun selectActivity(activity: ActionWithActivity): Flow<Exercise> =
        exerciseSource.setActivityIntoExercise(
            exerciseId = activity.exerciseId, activityId = activity.activity.idActivity)

    override fun changeSequenceExercise(item: DataForChangeSequence): Flow<Exercise> {
        TODO("Not yet implemented")
    }
}