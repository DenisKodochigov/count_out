package com.count_out.presentation.screens.settings

import com.count_out.domain.entity.TypeRepo
import com.count_out.domain.use_case.UseCase
import com.count_out.domain.use_case.bluetooth.GetConnectionStateUC
import com.count_out.domain.use_case.bluetooth.GetHeartRateUC
import com.count_out.domain.use_case.bluetooth.LastBleDeviceUC
import com.count_out.domain.use_case.bluetooth.StartScanBleUC
import com.count_out.domain.use_case.other.CollapsingUC
import com.count_out.domain.use_case.other.ShowBottomSheetUC
import com.count_out.domain.use_case.plans.activity.GetActivitiesUC
import com.count_out.domain.use_case.settings.GetSettingsUC
import com.count_out.domain.use_case.settings.UpdateSettingUC
import com.count_out.presentation.models.ActivityImplP
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
            is ShowBottomSheetUC.Response-> converterLocal(resultData, state)
            is StartScanBleUC.Response-> converterLocal(resultData, state)
            is GetHeartRateUC.Response-> converterLocal(resultData, state)
            is LastBleDeviceUC.Response-> converterLocal(resultData, state)
            is GetConnectionStateUC.Response-> converterLocal(resultData, state)
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
            state.value = state.value.copy(
                activities = (data.activity as TypeRepo.ActivitiesT).item.map { it as ActivityImplP})
        return state.value
    }
    private fun converterLocal(data: UpdateSettingUC.Response, state: MutableStateFlow<SettingsState>): SettingsState{
        if (data.setting is TypeRepo.SettingsT)
            state.value = state.value.copy( settings = (data.setting as TypeRepo.SettingsT).item)
        return state.value
    }
    private fun converterLocal(data: CollapsingUC.Response, state: MutableStateFlow<SettingsState>): SettingsState{
        if (data.collaps is TypeRepo.CollapsingT)
            state.value = state.value.copy( collapsing = (data.collaps as TypeRepo.CollapsingT).item)
        return state.value
    }
    private fun converterLocal(data: ShowBottomSheetUC.Response, state: MutableStateFlow<SettingsState>): SettingsState {
        if (data.show is TypeRepo.ShowBottomSheetT)
            state.value = state.value.copy( showBS = (data.show as TypeRepo.ShowBottomSheetT).item)
        return state.value
    }
    private fun converterLocal(data: StartScanBleUC.Response, state: MutableStateFlow<SettingsState>): SettingsState {
        if (data.result is TypeRepo.DevicesUIT)
            state.value = state.value.copy( devicesUI = (data.result as TypeRepo.DevicesUIT).item)
        return state.value
    }
    private fun converterLocal(data: GetConnectionStateUC.Response, state: MutableStateFlow<SettingsState>): SettingsState {
        if (data.result is TypeRepo.BleConnectStateT)
            state.value = state.value.copy( connectingState = (data.result as TypeRepo.BleConnectStateT).item)
        return state.value
    }
    private fun converterLocal(data: GetHeartRateUC.Response, state: MutableStateFlow<SettingsState>): SettingsState {
        if (data.result is TypeRepo.IntT)
            state.value = state.value.copy( heartRate = (data.result as TypeRepo.IntT).item)
        return state.value
    }
    private fun converterLocal(data: LastBleDeviceUC.Response, state: MutableStateFlow<SettingsState>): SettingsState {
        if (data.result is TypeRepo.DeviceUIT)
            state.value = state.value.copy( lastConnectHearthRateDevice = (data.result as TypeRepo.DeviceUIT).item)
        return state.value
    }
    private fun converterOther(state: MutableStateFlow<SettingsState>): SettingsState {
        return state.value
    }
}