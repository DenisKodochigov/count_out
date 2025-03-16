package com.count_out.data.repository

import com.count_out.data.source.local.SettingsSource
import com.count_out.domain.entity.Setting
import com.count_out.domain.entity.Settings
import com.count_out.domain.repository.trainings.SettingsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepoImpl @Inject constructor(private val settingsSource: SettingsSource): SettingsRepo {

    override fun getSettings(): Flow<Settings> = settingsSource.getSettings()
    override fun saveSetting(setting: Setting) {
        when(setting){
            is Setting.BleName -> { settingsSource.saveBleName(setting.value)}
            is Setting.BleAddress -> { settingsSource.saveBleAddress(setting.value)}
            is Setting.SpeechDescription -> { settingsSource.saveSettingSpeechDescr(setting.value)}
        }
    }
}