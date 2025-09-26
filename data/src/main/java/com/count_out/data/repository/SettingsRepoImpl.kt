package com.count_out.data.repository

import com.count_out.data.source.local.SettingsSource
import com.count_out.domain.entity.Settings
import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.entity.throwable.ResultUC
import com.count_out.domain.repository.plans.SettingsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SettingsRepoImpl @Inject constructor(
    private val settingsSource: SettingsSource): SettingsRepo, PrimeRepo() {

    override fun getSettings(): Flow<ResultUC<TypeRepo>> = settingsSource.getSettings().convertor()
    override fun saveSetting(setting: TypeRepo): Flow<ResultUC<TypeRepo>> {
        return if (setting is TypeRepo.SettingsT){
            when(setting.item){
                is Settings.NameBle -> {
                    settingsSource.saveBleName(toTypeSource(setting)).convertor()}
                is Settings.AddressBle -> {
                    settingsSource.saveBleAddress(toTypeSource(setting)).convertor()}
                is Settings.SpeechDescription -> {
                    settingsSource.saveSettingSpeechDescr(toTypeSource(setting)).convertor()}
            }
        } else flowOf(throwableNull)
    }
}