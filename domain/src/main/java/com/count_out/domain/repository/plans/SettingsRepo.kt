package com.count_out.domain.repository.plans

import com.count_out.domain.entity.Setting
import com.count_out.domain.entity.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepo {
    fun getSettings(): Flow<Settings>
//    fun getSetting(setting: Setting): Flow<Boolean>
    fun saveSetting(setting: Setting)
}