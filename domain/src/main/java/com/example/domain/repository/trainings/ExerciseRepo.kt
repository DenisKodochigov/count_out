package com.example.domain.repository.trainings

import com.example.domain.entity.ActionWithActivity
import com.example.domain.entity.DataForChangeSequence
import com.example.domain.entity.Exercise
import com.example.domain.entity.Training
import kotlinx.coroutines.flow.Flow

interface ExerciseRepo {
    fun get(id: Long): Flow<Exercise>
    fun gets(): Flow<List<Exercise>>
    fun del(exercise: Exercise): Flow<List<Training>>
    fun add(exercise: Exercise): Flow<List<Exercise>>
    fun copy(exercise: Exercise): Flow<List<Exercise>>
    fun update(exercise: Exercise): Flow<Exercise>
    fun selectActivity(activity: ActionWithActivity): Flow<Exercise>
    fun changeSequenceExercise(item: DataForChangeSequence): Flow<Exercise>

    fun getForRound(id: Long): Flow<List<Exercise>>
    fun getForRing(id: Long): Flow<List<Exercise>>
    fun getFilter(list: List<Long>): Flow<List<Exercise>>
//    fun delRound(idRound: Long)
//    fun delRing(idRing: Long) roundId: Long = 0, ringId: Long = 0
}