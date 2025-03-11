package com.count_out.data.source.room

import com.count_out.data.models.SetImpl
import com.count_out.domain.entity.workout.ActionWithSet
import com.count_out.domain.entity.workout.Set
import kotlinx.coroutines.flow.Flow

interface SetSource {
    fun gets(exerciseId: Long): Flow<List<Set>>
    fun get(id: Long): Flow<Set>
    fun add( item: ActionWithSet): Flow<List<SetImpl>>
    fun copy( item: ActionWithSet): Flow<List<SetImpl>>
    fun del( item: ActionWithSet): Flow<List<SetImpl>>
    fun update( item: ActionWithSet): Flow<Set>
}