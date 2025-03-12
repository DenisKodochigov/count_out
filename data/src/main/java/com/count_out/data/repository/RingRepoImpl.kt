package com.count_out.data.repository

import com.count_out.data.models.RingImpl
import com.count_out.data.source.room.RingSource
import com.count_out.domain.entity.workout.Ring
import com.count_out.domain.repository.trainings.RingRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RingRepoImpl @Inject constructor(private val ringSource: RingSource): RingRepo {
    override fun get(ring: Ring): Flow<Ring> = ringSource.get(ring)

    override fun gets(trainingId: Long): Flow<List<Ring>> = ringSource.gets(trainingId)

    override fun del(ring: Ring): Flow<List<Ring>> {
        ringSource.del(ring as RingImpl)
        return ringSource.gets(ring.trainingId)
    }

    override fun copy(ring: Ring): Flow<List<Ring>> {
        ringSource.copy(ring as RingImpl)
        return ringSource.gets(ring.trainingId)
    }

    override fun update(ring: Ring): Flow<List<Ring>> {
        ringSource.update(ring as RingImpl)
        return ringSource.gets(ring.trainingId)
    }
}