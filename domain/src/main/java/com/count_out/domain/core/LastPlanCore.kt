package com.count_out.domain.core

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.LastPlanRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LastPlanCore @Inject constructor(private val repo: LastPlanRepo): Core() {
    fun getLastUsedPlan(): Flow<ResultUC<TypeRepo>>{
        return repo.getLastUsedPlan() }
    fun saveLastUsedPlan(id: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return repo.saveLastUsedPlan(id) }
}