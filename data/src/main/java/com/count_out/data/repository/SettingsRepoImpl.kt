package com.count_out.data.repository

import com.count_out.data.source.room.SetSource
import com.count_out.domain.entity.SettingRecord
import com.count_out.domain.entity.Settings
import com.count_out.domain.repository.trainings.SettingsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepoImpl@Inject constructor(private val setSource: SetSource): SettingsRepo {
    override fun getSettings(): Flow<Settings> {
        TODO("Not yet implemented")
    }

    override fun getSetting(setting: SettingRecord): Flow<SettingRecord> {
        TODO("Not yet implemented")
    }

    override fun updateSetting(setting: SettingRecord): Flow<SettingRecord> {
        TODO("Not yet implemented")
    }
}