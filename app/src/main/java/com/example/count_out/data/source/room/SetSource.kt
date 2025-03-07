package com.example.count_out.data.source.room

import com.example.count_out.entity.workout.ActionWithSet
import com.example.count_out.entity.workout.Set
import kotlinx.coroutines.flow.Flow

interface SetSource {
    fun gets(exerciseId: Long): Flow<List<Set>>
    fun get(id: Long): Flow<Set>
    fun add( item: ActionWithSet): Flow<List<Set>>
    fun copy( item: ActionWithSet): Flow<List<Set>>
    fun del( item: ActionWithSet): Flow<List<Set>>
    fun update( item: ActionWithSet): Flow<Set>
}