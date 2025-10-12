package com.count_out.domain.core.plans

import com.count_out.domain.core.Core
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.plans.SettingsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsCore @Inject constructor(private val repo: SettingsRepo): Core() {
    fun getSettings(): Flow<ResultDomain<Domain>>{
        return repo.getSettings() }
    fun saveSetting(setting: Domain): Flow<ResultDomain<Domain>>{
        return repo.saveSetting(setting) }
}