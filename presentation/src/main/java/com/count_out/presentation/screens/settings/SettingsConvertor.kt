package com.count_out.presentation.screens.settings

import com.count_out.domain.entity.Settings
import com.count_out.domain.entity.bluetooth.BleDataMap
import com.count_out.domain.entity.enums.ConnectState
import com.count_out.domain.entity.router.DeviceBle
import com.count_out.domain.entity.types_domai.LongDm
import com.count_out.domain.entity.workout.Activities
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.LauncherBS
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.bluetooth.GetConnectionStateUC
import com.count_out.domain.use_case.bluetooth.GetHeartRateUC
import com.count_out.domain.use_case.bluetooth.LastBleDeviceUC
import com.count_out.domain.use_case.bluetooth.StartScanBleUC
import com.count_out.domain.use_case.other.CollapsingUC
import com.count_out.domain.use_case.other.LauncherBSUC
import com.count_out.domain.use_case.plans.activity.GetActivitiesUC
import com.count_out.domain.use_case.settings.GetSettingsUC
import com.count_out.domain.use_case.settings.UpdateSettingUC
import com.count_out.presentation.models.LauncherBSp
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
            is StartScanBleUC.Response-> converterLocal(resultData, state)
            is GetHeartRateUC.Response-> converterLocal(resultData, state)
            is LastBleDeviceUC.Response-> converterLocal(resultData, state)
            is GetConnectionStateUC.Response-> converterLocal(resultData, state)
            is LauncherBSUC.Response-> converterLocal(resultData, state)
            else -> converterOther(state)
        }
    }
    private fun converterLocal(data: LauncherBSUC.Response, state: MutableStateFlow<SettingsState>): SettingsState {
        if (data.launcher is LauncherBS<*>) {
            state.value = state.value.copy(launcherBS = data.launcher as LauncherBSp) }
        return state.value
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
    private fun converterLocal(data: GetHeartRateUC.Response, state: MutableStateFlow<SettingsState>): SettingsState{
        if (data.result is LongDm) { state.value = state.value.copy(heartRate = (data.result as LongDm).item.toInt()) }
        return state.value
    }
    private fun converterLocal(data: CollapsingUC.Response, state: MutableStateFlow<SettingsState>): SettingsState{
        if (data.collaps is Collapsing)
            state.value = state.value.copy( collapsing = data.collaps as Collapsing)
        return state.value
    }
    private fun converterLocal(data: GetConnectionStateUC.Response, state: MutableStateFlow<SettingsState>): SettingsState {
        if (data.result is LongDm) state.value = state.value.copy( connectingState = ConnectState.entries[(data.result as LongDm).item.toInt()] )
        return state.value
    }
    private fun converterLocal(data: LastBleDeviceUC.Response, state: MutableStateFlow<SettingsState>): SettingsState {
        if (data.result is DeviceBle) {
            state.value = state.value.copy(lastConnectHearthRateDevice = data.result as DeviceBle?)
        }
        return state.value
    }
    private fun converterLocal(data: StartScanBleUC.Response, state: MutableStateFlow<SettingsState>): SettingsState {
        if (data.result is BleDataMap) {
            state.value = state.value.copy(devicesUI = (data.result as BleDataMap).item.map { (_, value) -> value })
        }
        return state.value
    }
    private fun converterOther(state: MutableStateFlow<SettingsState>): SettingsState {
        return state.value
    }
}
