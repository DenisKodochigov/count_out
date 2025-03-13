package com.count_out.domain.repository.trainings

import com.count_out.domain.entity.workout.Set
import com.count_out.domain.entity.workout.Training
import kotlinx.coroutines.flow.Flow

interface SetRepo {
    fun copy(set: Set): Flow<Training>
    fun gets(exerciseId: Long): Flow<Training>
    fun get(set: Set): Flow<Set>
    fun del(set: Set): Flow<Training>
    fun update(set: Set): Flow<Training>
}