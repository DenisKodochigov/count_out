package com.count_out.domain.repository.plans

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import kotlinx.coroutines.flow.Flow

interface SettingsRepo {
    fun getSettings(): Flow<ResultUC<TypeRepo>>
    fun saveSetting(setting: TypeRepo): Flow<ResultUC<TypeRepo>>
}