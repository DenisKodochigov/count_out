package com.example.data.repository

import com.example.data.entity.RingImpl
import com.example.data.source.room.RingSource
import com.example.domain.entity.Ring
import com.example.domain.repository.training.RingRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RingRepoImpl @Inject constructor(private val ringSource: RingSource): RingRepo {
    override fun get(id: Long): Flow<Ring> = ringSource.get(id)

    override fun gets(trainingId: Long): Flow<List<Ring>> = ringSource.gets(trainingId)

    override fun del(ring: Ring) = ringSource.del(ring as RingImpl)

    override fun copy(ring: Ring): Flow<List<Ring>> = ringSource.copy(ring as RingImpl)

    override fun add(ring: Ring): Flow<List<Ring>> = ringSource.add(ring as RingImpl)

    override fun update(ring: Ring): Flow<Ring> = ringSource.update(ring as RingImpl)
}