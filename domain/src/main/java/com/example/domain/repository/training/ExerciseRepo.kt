package com.example.domain.repository.training

import com.example.domain.entity.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepo {
    fun get(id: Long): Flow<Exercise>
    fun gets(): Flow<List<Exercise>>
    fun del(exercise: Exercise)
    fun addCopy(exercise: Exercise): Flow<List<Exercise>>
    fun update(exercise: Exercise): Flow<Exercise>
    fun setActivity(exerciseId: Long, activityId: Long): Flow<Exercise>

    fun getForRound(id: Long): Flow<List<Exercise>>
    fun getForRing(id: Long): Flow<List<Exercise>>
    fun getFilter(list: List<Long>): Flow<List<Exercise>>
//    fun delRound(idRound: Long)
//    fun delRing(idRing: Long)
}