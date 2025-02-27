package com.count_out.domain.repository.trainings

import com.count_out.domain.entity.SettingRecord
import com.count_out.domain.entity.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepo {
    fun getSettings(): Flow<Settings>
    fun getSetting(setting: SettingRecord): Flow<SettingRecord>
    fun updateSetting(setting: SettingRecord): Flow<SettingRecord>
}