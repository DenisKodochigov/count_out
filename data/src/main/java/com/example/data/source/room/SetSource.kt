package com.example.data.source.room

import com.example.data.entity.SetImpl
import com.example.domain.entity.ActionWithSet
import com.example.domain.entity.Set
import kotlinx.coroutines.flow.Flow

interface SetSource {
    fun gets(exerciseId: Long): Flow<List<Set>>
    fun get(id: Long): Flow<Set>
    fun add( item:ActionWithSet): Flow<List<SetImpl>>
    fun copy( item:ActionWithSet): Flow<List<SetImpl>>
    fun del( item:ActionWithSet)
    fun update( item:ActionWithSet): Flow<Set>
}