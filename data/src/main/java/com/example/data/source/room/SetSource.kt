package com.example.data.source.room

import com.example.data.entity.SetImpl
import com.example.domain.entity.Set
import kotlinx.coroutines.flow.Flow

interface SetSource {
    fun gets(exerciseId: Long): Flow<List<Set>>
    fun get(id: Long): Flow<Set>
    fun addCopy(set: SetImpl): Flow<List<SetImpl>>
    fun del(set: SetImpl)
    fun update(set: SetImpl): Flow<Set>
}