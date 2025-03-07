package com.example.count_out.domain.repository.trainings

import com.example.count_out.entity.workout.ActionWithSet
import com.example.count_out.entity.workout.Set
import kotlinx.coroutines.flow.Flow

interface SetRepo {
    fun add(item: ActionWithSet): Flow<List<Set>>
    fun copy( item: ActionWithSet): Flow<List<Set>>
    fun gets(exerciseId: Long): Flow<List<Set>>
    fun get(id: Long): Flow<Set>
    fun del( item: ActionWithSet): Flow<List<Set>>
    fun update( item: ActionWithSet): Flow<Set>
}