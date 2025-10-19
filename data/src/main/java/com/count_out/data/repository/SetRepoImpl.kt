package com.count_out.data.repository

import com.count_out.data.models.ResultData.Companion.convertor
import com.count_out.data.models.entity.SetDb
import com.count_out.data.source.room.SetSource
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.plans.SetRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetRepoImpl @Inject constructor(
    private val source: SetSource): SetRepo {

    override fun copy(set: Domain): Flow<ResultDomain<Domain>> {
        return source.copy(SetDb.fromDomain(set)).convertor()
    }

    override fun del(set: Domain): Flow<ResultDomain<Domain>> {
        return source.del(SetDb.fromDomain(set)).convertor()
    }
    override fun update(set: Domain): Flow<ResultDomain<Domain>> {
        return source.update(SetDb.fromDomain(set)).convertor()
    }
}