package com.count_out.domain.repository.trainings

import com.count_out.domain.entity.workout.Set
import kotlinx.coroutines.flow.Flow

interface SetRepo {
    fun copy(set: Set): Flow<List<Set>>
    fun gets(exerciseId: Long): Flow<List<Set>>
    fun get(set: Set): Flow<Set>
    fun del(set: Set): Flow<List<Set>>
    fun update(set: Set): Flow<Set>
}