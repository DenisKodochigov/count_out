package com.count_out.domain.repository.trainings

import com.count_out.domain.entity.workout.Ring
import kotlinx.coroutines.flow.Flow

interface RingRepo {
    fun get(ring: Ring): Flow<Ring>
    fun gets(trainingId: Long): Flow<List<Ring>>
    fun del(ring: Ring): Flow<List<Ring>>
    fun copy(ring: Ring): Flow<List<Ring>>
    fun update(ring: Ring): Flow<List<Ring>>
}