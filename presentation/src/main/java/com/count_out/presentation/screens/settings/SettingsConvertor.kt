package com.count_out.presentation.screens.settings

import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.settings.GetSettingsUC
import com.count_out.domain.use_case.settings.UpdateSettingUC
import com.count_out.presentation.screens.prime.PrimeConvertor
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SettingsConvertor @Inject constructor():
    PrimeConvertor<UseCase.Response, SettingsState>() {

    override fun makeSuccess(resultData: UseCase.Response, state: MutableStateFlow<SettingsState>): SettingsState {
        return when(resultData){
            is GetSettingsUC.Response-> converterLocal(resultData, state)
            is UpdateSettingUC.Response-> converterLocal(resultData, state)
            else -> converterOther(state)
        }
    }

    private fun converterLocal(data: GetSettingsUC.Response, state: MutableStateFlow<SettingsState>): SettingsState{
        return state.value.copy(settings = data.setting)
    }
    private fun converterLocal(data: UpdateSettingUC.Response, state: MutableStateFlow<SettingsState>): SettingsState{
        return state.value.copy(settings = data.setting)
    }
    private fun converterOther(state: MutableStateFlow<SettingsState>): SettingsState {
        return state.value
    }
}