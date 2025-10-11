package com.count_out.data.repository

import com.count_out.data.models.ResultData.Companion.convertorFlow
import com.count_out.data.source.services.CountOutServiceSource
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.CountOutServiceRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CountOutServiceRepoImpl @Inject constructor(
    private val source: CountOutServiceSource
): CountOutServiceRepo {
    override fun bind(): Flow<ResultDomain<Domain>> = source.bind().convertorFlow()
    override fun unbind(): Flow<ResultDomain<Domain>> = source.unbind().convertorFlow()
}