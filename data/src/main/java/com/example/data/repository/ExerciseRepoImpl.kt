package com.example.data.repository

import com.example.data.entity.ExerciseImpl
import com.example.data.source.room.ExerciseSource
import com.example.domain.entity.Exercise
import com.example.domain.repository.trainings.ExerciseRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseRepoImpl @Inject constructor(private val exerciseSource: ExerciseSource): ExerciseRepo {
    override fun get(id: Long): Flow<Exercise> = exerciseSource.get(id)

    override fun gets(): Flow<List<Exercise>> = exerciseSource.gets()

    override fun del(exercise: Exercise) { exerciseSource.del(exercise as ExerciseImpl) }

    override fun add(roundId: Long, ringId: Long): Flow<List<Exercise>> = exerciseSource.add(roundId, ringId)

    override fun copy(exercise: Exercise): Flow<List<Exercise>> = exerciseSource.copy(exercise as ExerciseImpl)

    override fun update(exercise: Exercise): Flow<Exercise> = exerciseSource.update(exercise as ExerciseImpl)

    override fun setActivity(exerciseId: Long, activityId: Long): Flow<Exercise> =
        exerciseSource.setActivityIntoExercise(exerciseId,activityId)

    override fun getForRound(id: Long): Flow<List<Exercise>> = exerciseSource.getForRound(id)

    override fun getForRing(id: Long): Flow<List<Exercise>> = exerciseSource.getForRing(id)

    override fun getFilter(list: List<Long>): Flow<List<Exercise>> = exerciseSource.getFilter(list)

//    override fun delRound(idRound: Long) { exerciseSource.delRound(idRound) }
//
//    override fun delRing(idRing: Long) { exerciseSource.delRing(idRing) }
}