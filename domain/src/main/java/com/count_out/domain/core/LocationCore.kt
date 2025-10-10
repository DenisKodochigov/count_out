package com.count_out.domain.core

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.repository.LocationRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationCore  @Inject constructor(private val repo: LocationRepo): Core() {
    fun getLocation(): Flow<ResultDomain<TypeRepo>>{
        return repo.getLocation() }
}