package com.example.domain.repository.training

import com.example.domain.entity.Ring
import kotlinx.coroutines.flow.Flow

interface RingRepo {
    fun get(id: Long): Flow<Ring>
    fun gets(trainingId: Long): Flow<List<Ring>>
    fun del(ring: Ring)
    fun addCopy(ring: Ring): Flow<List<Ring>>
    fun update(ring: Ring): Flow<Ring>
}