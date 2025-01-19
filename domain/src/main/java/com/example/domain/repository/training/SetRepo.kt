package com.example.domain.repository.training

import com.example.domain.entity.Set
import kotlinx.coroutines.flow.Flow

interface SetRepo {
    fun addCopy(set: Set): Flow<List<Set>>
    fun gets(exerciseId: Long): Flow<List<Set>>
    fun get(id: Long): Flow<Set>
    fun del(set: Set)
    fun update(set: Set): Flow<Set>
}