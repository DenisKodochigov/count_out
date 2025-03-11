package com.count_out.data.source.room

import com.count_out.data.models.RingImpl
import com.count_out.domain.entity.workout.Ring
import kotlinx.coroutines.flow.Flow

interface RingSource {
    fun get(id: Long): Flow<Ring>
    fun gets(trainingId: Long): Flow<List<Ring>>
    fun del(ring: RingImpl)
    fun add(trainingId: Long): Flow<List<Ring>>
    fun copy(ring: RingImpl): Flow<List<Ring>>
    fun update(ring: RingImpl): Flow<Ring>
}