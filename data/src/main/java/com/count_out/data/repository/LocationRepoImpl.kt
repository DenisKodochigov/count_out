package com.count_out.data.repository

import com.count_out.data.models.ResultData.Companion.convertorFlow
import com.count_out.data.source.framework.LocationSource
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.LocationRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationRepoImpl @Inject constructor(private val locationSource: LocationSource): LocationRepo {
    override fun getLocation(): Flow<ResultDomain<Domain>> {
        return locationSource.getLocation().convertorFlow()
    }
}