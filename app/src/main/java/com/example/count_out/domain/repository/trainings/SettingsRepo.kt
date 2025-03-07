package com.example.count_out.domain.repository.trainings

import com.example.count_out.entity.settings.SettingRecord
import com.example.count_out.entity.settings.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepo {
    fun getSettings(): Flow<Settings>
    fun getSetting(setting: SettingRecord): Flow<SettingRecord>
    fun updateSetting(setting: SettingRecord): Flow<SettingRecord>
}