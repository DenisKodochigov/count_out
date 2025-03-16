package com.count_out.presentation.screens.settings

import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.settings.GetSettingsUC
import com.count_out.domain.use_case.settings.UpdateSettingUC
import com.count_out.presentation.screens.prime.PrimeConvertor
import javax.inject.Inject

class SettingsConvertor @Inject constructor():
    PrimeConvertor<UseCase.Response, SettingsState>() {

    val state = SettingsState()
    override fun convertSuccess(data: UseCase.Response): SettingsState {
        return when(data){
            is GetSettingsUC.Response-> converterLocal(data)
            is UpdateSettingUC.Response-> converterLocal(data)
            else -> converterOther()
        }
    }

    private fun converterLocal(data: GetSettingsUC.Response): SettingsState{
        return state.copy(settings = data.setting)
    }
    private fun converterLocal(data: UpdateSettingUC.Response): SettingsState{
        return state.copy(settings = data.setting)
    }
    private fun converterOther(): SettingsState {
        return state
    }
}