package com.count_out.data.repository

import com.count_out.data.source.room.RingSource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.repository.plans.RingRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RingRepoImpl @Inject constructor(private val source: RingSource): RingRepo, PrimeRepo() {
    override fun get(ring: TypeRepo): Flow<ResultUC<TypeRepo>> =
        source.get(toTypeSource(ring)).convertor()

    override fun gets(trainingId: TypeRepo): Flow<ResultUC<TypeRepo>> =
        source.get(toTypeSource(trainingId)).convertor()

    override fun del(ring: TypeRepo): Flow<ResultUC<TypeRepo>> =
        source.del(toTypeSource(ring))
            .nextActionOk { source.gets(toTypeSource(ring)) }

    override fun copy(ring: TypeRepo): Flow<ResultUC<TypeRepo>> =
        source.copy(toTypeSource(ring))
            .nextAction { source.gets(toTypeSource(ring)) }


    override fun update(ring: TypeRepo): Flow<ResultUC<TypeRepo>> =
        source.update(toTypeSource(ring))
            .nextActionOk { source.gets(toTypeSource(ring)) }
}