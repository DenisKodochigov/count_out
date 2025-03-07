package com.example.count_out.data.repository

import com.example.count_out.domain.repository.trainings.RingRepo
import com.example.count_out.data.source.room.RingSource
import com.example.count_out.entity.models.RingImpl
import com.example.count_out.entity.workout.Ring
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RingRepoImpl @Inject constructor(private val ringSource: RingSource): RingRepo {
    override fun get(id: Long): Flow<Ring> = ringSource.get(id)

    override fun gets(trainingId: Long): Flow<List<Ring>> = ringSource.gets(trainingId)

    override fun del(ring: Ring) = ringSource.del(ring as RingImpl)

    override fun add(trainingId: Long): Flow<List<Ring>> = ringSource.add(trainingId)
    override fun copy(ring: Ring): Flow<List<Ring>> = ringSource.copy(ring as RingImpl)

    override fun update(ring: Ring): Flow<Ring> = ringSource.update(ring as RingImpl)
}