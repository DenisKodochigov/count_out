package com.example.count_out.data.source.room

import com.example.count_out.entity.workout.Ring
import kotlinx.coroutines.flow.Flow

interface RingSource {
    fun get(id: Long): Flow<Ring>
    fun gets(trainingId: Long): Flow<List<Ring>>
    fun del(ring: Ring)
    fun add(trainingId: Long): Flow<List<Ring>>
    fun copy(ring: Ring): Flow<List<Ring>>
    fun update(ring: Ring): Flow<Ring>
}