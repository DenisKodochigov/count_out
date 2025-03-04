package com.count_out.domain.repository.trainings

import com.count_out.entity.entity.workout.ActionWithSet
import com.count_out.entity.entity.workout.Set
import kotlinx.coroutines.flow.Flow

interface SetRepo {
    fun add(item: ActionWithSet): Flow<List<Set>>
    fun copy( item: ActionWithSet): Flow<List<Set>>
    fun gets(exerciseId: Long): Flow<List<Set>>
    fun get(id: Long): Flow<Set>
    fun del( item: ActionWithSet): Flow<List<Set>>
    fun update( item: ActionWithSet): Flow<Set>
}