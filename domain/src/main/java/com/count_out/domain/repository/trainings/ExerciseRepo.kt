package com.count_out.domain.repository.trainings

import com.count_out.domain.entity.DataForChangeSequence
import com.count_out.domain.entity.workout.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepo {
    fun get(exercise: Exercise): Flow<Exercise>
    fun del(exercise: Exercise): Flow<List<Exercise>>
    fun copy(exercise: Exercise): Flow<List<Exercise>>
    fun update(exercise: Exercise): Flow<Exercise>
    fun changeSequenceExercise(item: DataForChangeSequence): Flow<List<Exercise>>

    fun getForRound(id: Long): Flow<List<Exercise>>
    fun getForRing(id: Long): Flow<List<Exercise>>
    fun getFilter(list: List<Long>): Flow<List<Exercise>>
//    fun delRound(idRound: Long)
//    fun delRing(idRing: Long) roundId: Long = 0, ringId: Long = 0
}