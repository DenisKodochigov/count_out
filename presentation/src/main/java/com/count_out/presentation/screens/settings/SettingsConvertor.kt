package com.count_out.presentation.screens.settings

import com.count_out.domain.repository.TypeRepo
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.plans.activity.GetActivitiesUC
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
            is GetActivitiesUC.Response-> converterLocal(resultData, state)
            is UpdateSettingUC.Response-> converterLocal(resultData, state)
            else -> converterOther(state)
        }
    }

    private fun converterLocal(data: GetSettingsUC.Response, state: MutableStateFlow<SettingsState>): SettingsState{
        if (data.setting is TypeRepo.SettingsT)
            state.value = state.value.copy( settings = (data.setting as TypeRepo.SettingsT).item)
        return state.value
    }
    private fun converterLocal(data: GetActivitiesUC.Response, state: MutableStateFlow<SettingsState>): SettingsState{
        if (data.activity is TypeRepo.ActivitiesT)
            state.value = state.value.copy( activities = (data.activity as TypeRepo.ActivitiesT).item)
        return state.value
    }
    private fun converterLocal(data: UpdateSettingUC.Response, state: MutableStateFlow<SettingsState>): SettingsState{
        if (data.setting is TypeRepo.SettingsT)
            state.value = state.value.copy( settings = (data.setting as TypeRepo.SettingsT).item)
        return state.value
    }
    private fun converterOther(state: MutableStateFlow<SettingsState>): SettingsState {
        return state.value
    }
}