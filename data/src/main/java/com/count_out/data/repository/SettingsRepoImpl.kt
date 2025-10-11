package com.count_out.data.repository

import com.count_out.data.models.Data.Companion.toData
import com.count_out.data.models.ResultData.Companion.convertorFlow
import com.count_out.data.source.local.SettingsSource
import com.count_out.domain.entity.Settings
import com.count_out.domain.entity.throwable.ResultDomain
import com.count_out.domain.entity.throwable.ThrowableUC
import com.count_out.domain.entity.workout.Domain
import com.count_out.domain.repository.plans.SettingsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SettingsRepoImpl @Inject constructor(
    private val settingsSource: SettingsSource): SettingsRepo {

    override fun getSettings(): Flow<ResultDomain<Domain>> = settingsSource.getSettings().convertorFlow()
    override fun saveSetting(setting: Domain): Flow<ResultDomain<Domain>> {
        return if (setting is Settings){
            when(setting){
                is Settings.NameBle -> {
                    settingsSource.saveBleName(toData(setting)).convertorFlow()}
                is Settings.AddressBle -> {
                    settingsSource.saveBleAddress(toData(setting)).convertorFlow()}
                is Settings.SpeechDescription -> {
                    settingsSource.saveSettingSpeechDescr(toData(setting)).convertorFlow()}
            }
        } else flowOf(ResultDomain.Error(ThrowableUC.extract(Exception("return null"))))
    }
}