package com.count_out.data.repository

import com.count_out.data.source.framework.LocationSource
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.LocationRepo
import com.count_out.domain.entity.TypeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationRepoImpl @Inject constructor(private val locationSource: LocationSource): LocationRepo {
    override fun getLocation(): Flow<ResultUC<TypeRepo>> {
        return flow { emit(ResultUC.Success(TypeRepo.BooleanT(true))) }
    }
}