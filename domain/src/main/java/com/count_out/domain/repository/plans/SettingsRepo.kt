package com.count_out.domain.repository.plans

import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.TypeRepo
import kotlinx.coroutines.flow.Flow

interface SettingsRepo {
    fun getSettings(): Flow<ResultUC<TypeRepo>>
//    fun getSetting(setting: Setting): Flow<Boolean>
    suspend fun saveSetting(setting: TypeRepo): Flow<ResultUC<TypeRepo>>
}