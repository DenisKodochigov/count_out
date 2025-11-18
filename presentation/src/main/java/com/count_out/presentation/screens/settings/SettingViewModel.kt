package com.count_out.presentation.screens.settings

import androidx.lifecycle.SavedStateHandle
import com.count_out.domain.use_case.bluetooth.ClearCacheBleUC
import com.count_out.domain.use_case.bluetooth.ConnectDeviceHrUC
import com.count_out.domain.use_case.bluetooth.GetConnectionStateUC
import com.count_out.domain.use_case.bluetooth.GetHeartRateUC
import com.count_out.domain.use_case.bluetooth.LastBleDeviceUC
import com.count_out.domain.use_case.bluetooth.SelectDeviceBleUC
import com.count_out.domain.use_case.bluetooth.StartScanBleUC
import com.count_out.domain.use_case.bluetooth.StopScanBleUC
import com.count_out.domain.use_case.other.CollapsingUC
import com.count_out.domain.use_case.other.LauncherBSUC
import com.count_out.domain.use_case.plans.activity.AddActivityUC
import com.count_out.domain.use_case.plans.activity.DeleteActivityUC
import com.count_out.domain.use_case.plans.activity.GetActivitiesUC
import com.count_out.domain.use_case.plans.activity.UpdateActivityUC
import com.count_out.domain.use_case.settings.GetSettingsUC
import com.count_out.domain.use_case.settings.UpdateSettingUC
import com.count_out.presentation.screens.prime.Event
import com.count_out.presentation.screens.prime.PrimeViewModel
import com.count_out.presentation.screens.prime.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val addActivity: AddActivityUC,
    private val delActivity: DeleteActivityUC,
    private val getsActivity: GetActivitiesUC,
    private val updateActivity: UpdateActivityUC,
    private val clearCacheBle: ClearCacheBleUC,
    private val startScanBle: StartScanBleUC,
    private val stopScanBle: StopScanBleUC,
    private val selectDeviceBle: SelectDeviceBleUC,
    private val getSettings: GetSettingsUC,
    private val updateSetting: UpdateSettingUC,
    private val collapsingSet: CollapsingUC,
    private val getConnectionState: GetConnectionStateUC,
    private val subscribeHeartRate: GetHeartRateUC,
    private val getLastBleDevice: LastBleDeviceUC,
    private val connectDeviceHr: ConnectDeviceHrUC,
    private val launcherBS: LauncherBSUC,
): PrimeViewModel<SettingsState, SettingsConvertor>() {

    override fun initScreenState(): ScreenState<SettingsState> = ScreenState.Loading
    override fun initDataState(): SettingsState = SettingsState(event = { submitEvent(it) })
    override fun convertor(): SettingsConvertor = SettingsConvertor()
    override fun routeEvent(event: Event) {
        when (event) {
//            is SettingsEvent.BackScreen -> { navigate.backStack() }
            is SettingsEvent.AddActivity -> { run(addActivity, AddActivityUC.Request(event.activity))}
            is SettingsEvent.DeleteActivity -> { run(delActivity,DeleteActivityUC.Request(event.activity))}
            is SettingsEvent.UpdateActivity -> { run(updateActivity, UpdateActivityUC.Request(event.activity)) }
            is SettingsEvent.UpdateSetting -> { run(updateSetting,UpdateSettingUC.Request(event.setting)) }
            is SettingsEvent.GetSettings -> { run(getSettings,GetSettingsUC.Request) }
            is SettingsEvent.StartScanBLE -> {run( startScanBle,StartScanBleUC.Request) }
            is SettingsEvent.StopScanBLE -> { run(stopScanBle,StopScanBleUC.Request) }
            is SettingsEvent.SelectDevice -> { run(selectDeviceBle,SelectDeviceBleUC.Request(event.device)) }
            is SettingsEvent.ClearCacheBLE -> { run(clearCacheBle,ClearCacheBleUC.Request) }
            is SettingsEvent.SetCollapsing -> { run(collapsingSet,CollapsingUC.Request(event.item)) }
            is SettingsEvent.Launcher -> { run(launcherBS, LauncherBSUC.Request(event.item))}
        }
    }
    init {
        template{getSettings.execute(GetSettingsUC.Request) }
        template{getsActivity.execute(GetActivitiesUC.Request) }
        template{getConnectionState.execute(GetConnectionStateUC.Request)}
        template{subscribeHeartRate.execute(GetHeartRateUC.Request) }
        template{getLastBleDevice.execute(LastBleDeviceUC.Request) }
    }
}