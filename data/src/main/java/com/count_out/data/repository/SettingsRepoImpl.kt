package com.count_out.data.repository

import com.count_out.data.source.local.SettingsSource
import com.count_out.domain.entity.Setting
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.repository.plans.SettingsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SettingsRepoImpl @Inject constructor(
    private val settingsSource: SettingsSource): SettingsRepo, PrimeRepo() {

    override fun getSettings(): Flow<ResultUC<TypeRepo>> = settingsSource.getSettings().convertor()
    override suspend fun saveSetting(setting: TypeRepo): Flow<ResultUC<TypeRepo>> {
        val convertorType = toTypeSource(setting)
        return if (setting is TypeRepo.SettingT){
            when(setting.item){
                is Setting.BleName -> {
                    settingsSource.saveBleName(convertorType).convertor()}
                is Setting.BleAddress -> {
                    settingsSource.saveBleAddress(convertorType).convertor()}
                is Setting.SpeechDescription -> {
                    settingsSource.saveSettingSpeechDescr(convertorType).convertor()}
            }
        } else flow { emit(throwableNull) }

    }
}