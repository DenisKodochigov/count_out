package com.count_out.data.repository

import com.count_out.data.models.ResultData.Companion.convertor
import com.count_out.data.models.entity.RingDb
import com.count_out.data.models.entity.SetViewIdDb
import com.count_out.data.source.room.RingSource
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.plans.RingRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RingRepoImpl @Inject constructor( private val source: RingSource): RingRepo {

    override fun del(ring: Domain): Flow<ResultDomain<Domain>> {
        return source.del( RingDb.fromDomain(ring)).convertor()
    }
    override fun insert(ring: Domain): Flow<ResultDomain<Domain>> {
        return source.insert(RingDb.fromDomain(ring)).convertor() }

    override fun changeSequence(setViewId: Domain): Flow<ResultDomain<Domain>> {
        return source.changeSequence( SetViewIdDb.fromDomain(setViewId)).convertor()
    }

    override fun update(ring: Domain): Flow<ResultDomain<Domain>> {
        return source.update(RingDb.fromDomain(ring)).convertor() }
}
//override fun select(training: Domain): Flow<ResultUC<Domain>> {
//        return source.copy(convertorType(training)).concatOk { source.gets() }
//        source.update(convertorType(training))
//        return source.gets()
//    }