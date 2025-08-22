package com.count_out.domain.core.plans

import com.count_out.domain.core.Core
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.repository.plans.ActivityRepo
import com.count_out.domain.repository.plans.SettingsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsCore @Inject constructor(private val repo: SettingsRepo): Core() {
    fun getSettings(): Flow<ResultUC<TypeRepo>>{
        return repo.getSettings() }
    fun saveSetting(setting: TypeRepo): Flow<ResultUC<TypeRepo>>{
        return repo.saveSetting(setting) }
}