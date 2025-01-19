package com.example.domain.repository.training

import com.example.domain.entity.Set
import kotlinx.coroutines.flow.Flow

interface SetRepo {
    fun add(set: Set): Flow<Set?>
    fun gets(exerciseId: Long): Flow<List<Set>>
    fun get(id: Long): Flow<Set>
    fun copy(set: Set, newExerciseId: Long): Flow<Set>
    fun del(set: Set)
    fun update(set: Set): Flow<Set>
}