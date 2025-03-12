package com.count_out.data.source.room

import com.count_out.domain.entity.workout.Ring
import kotlinx.coroutines.flow.Flow

interface RingSource {
    fun get(ring: Ring): Flow<Ring>
    fun gets(trainingId: Long): Flow<List<Ring>>
    fun del(ring: Ring)
    fun copy(ring: Ring): Long
    fun update(ring: Ring)
}