package com.count_out.domain.repository.plans

import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.workout.Domain
import kotlinx.coroutines.flow.Flow

interface SettingsRepo {
    fun getSettings(): Flow<ResultDomain<Domain>>
    fun saveSetting(setting: Domain): Flow<ResultDomain<Domain>>
}