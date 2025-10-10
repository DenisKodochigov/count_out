package com.count_out.data.repository

import com.count_out.data.source.framework.LocationSource
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.repository.LocationRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class LocationRepoImpl @Inject constructor(private val locationSource: LocationSource): LocationRepo {
    override fun getLocation(): Flow<ResultDomain<TypeRepo>> {
        return flowOf(ResultDomain.Success(TypeRepo.BooleanT(true)))
    }
}