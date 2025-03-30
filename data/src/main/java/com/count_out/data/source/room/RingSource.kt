package com.count_out.data.source.room

import com.count_out.data.models.RingImpl
import kotlinx.coroutines.flow.Flow

interface RingSource {
    fun get(ring: RingImpl): Flow<RingImpl?>
    fun gets(trainingId: Long): Flow<List<RingImpl>>
    fun del(ring: RingImpl)
    fun copy(ring: RingImpl): Long
    fun update(ring: RingImpl)
}