package com.example.data.source.room

import com.example.data.entity.RingImpl
import com.example.domain.entity.Ring
import kotlinx.coroutines.flow.Flow

interface RingSource {
    fun get(id: Long): Flow<Ring>
    fun gets(trainingId: Long): Flow<List<Ring>>
    fun del(ring: RingImpl)
    fun add(trainingId: Long): Flow<List<Ring>>
    fun copy(ring: RingImpl): Flow<List<Ring>>
    fun update(ring: RingImpl): Flow<Ring>
}