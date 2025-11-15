package com.count_out.presentation.screens.settings

import com.count_out.domain.entity.Settings
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.router.DeviceBle
import com.count_out.domain.entity.workout.Activities
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.bluetooth.GetConnectionStateUC
import com.count_out.domain.use_case.bluetooth.LastBleDeviceUC
import com.count_out.domain.use_case.other.CollapsingUC
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
            is CollapsingUC.Response-> converterLocal(resultData, state)
//            is ShowBottomSheetUC.Response-> converterLocal(resultData, state)
//            is StartScanBleUC.Response-> converterLocal(resultData, state)
//            is GetHeartRateUC.Response-> converterLocal(resultData, state)
            is LastBleDeviceUC.Response-> converterLocal(resultData, state)
            is GetConnectionStateUC.Response-> converterLocal(resultData, state)
            else -> converterOther(state)
        }
    }

    private fun converterLocal(data: GetSettingsUC.Response, state: MutableStateFlow<SettingsState>): SettingsState{
        if (data.setting is Settings) state.value = state.value.copy( settings = data.setting as Settings)
        return state.value
    }
    private fun converterLocal(data: GetActivitiesUC.Response, state: MutableStateFlow<SettingsState>): SettingsState{
        if (data.activity is Activities) state.value = state.value.copy(list = (data.activity as Activities).activities)
        return state.value
    }
    private fun converterLocal(data: UpdateSettingUC.Response, state: MutableStateFlow<SettingsState>): SettingsState{
        if (data.setting is Settings) state.value = state.value.copy( settings = data.setting as Settings)
        return state.value
    }
    private fun converterLocal(data: CollapsingUC.Response, state: MutableStateFlow<SettingsState>): SettingsState{
        if (data.collaps is Collapsing)
            state.value = state.value.copy( collapsing = data.collaps as Collapsing)
        return state.value
    }
    private fun converterLocal(data: GetConnectionStateUC.Response, state: MutableStateFlow<SettingsState>): SettingsState {
        if (data.result is ConnectState) state.value = state.value.copy( connectingState = data.result as ConnectState)
        return state.value
    }
    private fun converterLocal(data: LastBleDeviceUC.Response, state: MutableStateFlow<SettingsState>): SettingsState {
        if (data.result is DeviceBle) state.value = state.value.copy( lastConnectHearthRateDevice = data.result as DeviceBle?)
        return state.value
    }
    private fun converterOther(state: MutableStateFlow<SettingsState>): SettingsState {
        return state.value
    }
}
//    private fun converterLocal(data: ShowBottomSheetUC.Response, state: MutableStateFlow<SettingsState>): SettingsState {
//        if (data.show is ShowBottomSheet)
//            state.value = state.value.copy( showBS = data.show as ShowBottomSheet)
//        return state.value
//    }