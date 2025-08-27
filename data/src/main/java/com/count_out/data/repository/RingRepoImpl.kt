package com.count_out.data.repository

import com.count_out.data.source.room.RingSource
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.plans.RingRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RingRepoImpl @Inject constructor(private val source: RingSource): RingRepo, PrimeRepo() {

    override fun del(ring: TypeRepo): Flow<ResultUC<TypeRepo>> =
       wrapFlow(source.del(toTypeSource(ring)))

    override fun copy(ring: TypeRepo): Flow<ResultUC<TypeRepo>> =
        wrapFlow(source.copy(toTypeSource(ring)))

    override fun update(ring: TypeRepo): Flow<ResultUC<TypeRepo>> =
            wrapFlow(source.update(toTypeSource(ring)))
}
