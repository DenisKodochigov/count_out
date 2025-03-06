package com.example.count_out.ui.screens.settings

import androidx.lifecycle.viewModelScope
import com.example.count_out.domain.use_case.activity.AddActivityUC
import com.example.count_out.domain.use_case.activity.DeleteActivityUC
import com.example.count_out.domain.use_case.activity.GetsActivityUC
import com.example.count_out.domain.use_case.activity.SetColorActivityUC
import com.example.count_out.domain.use_case.activity.UpdateActivityUC
import com.example.count_out.domain.use_case.bluetooth.ClearCacheBleUC
import com.example.count_out.domain.use_case.bluetooth.SelectDeviceBleUC
import com.example.count_out.domain.use_case.bluetooth.StartScanBleUC
import com.example.count_out.domain.use_case.bluetooth.StopScanBleUC
import com.example.count_out.domain.use_case.settings.GetSettingUC
import com.example.count_out.domain.use_case.settings.GetSettingsUC
import com.example.count_out.domain.use_case.settings.UpdateSettingUC
import com.example.count_out.entity.bluetooth.DeviceUI
import com.example.count_out.entity.settings.SettingRecord
import com.example.count_out.entity.workout.Activity
import com.example.count_out.ui.screens.prime.Event
import com.example.count_out.ui.screens.prime.PrimeViewModel
import com.example.count_out.ui.screens.prime.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingViewModel @Inject constructor(
    private val converter: SettingsConvertor,
    private val addActivity: AddActivityUC,
    private val delActivity: DeleteActivityUC,
    private val getsActivity: GetsActivityUC,
    private val updateActivity: UpdateActivityUC,
    private val setColorActivity: SetColorActivityUC,
    private val clearCacheBle: ClearCacheBleUC,
    private val startScanBle: StartScanBleUC,
    private val stopScanBle: StopScanBleUC,
    private val selectDeviceBle: SelectDeviceBleUC,
    private val getSetting: GetSettingUC,
    private val getSettings: GetSettingsUC,
    private val updateSetting: UpdateSettingUC,
//    private val serviceBind: CountOutServiceBind,
//    private val dataRepository: DataRepository
): PrimeViewModel<SettingsState, ScreenState<SettingsState>>() {
    override fun initState(): ScreenState<SettingsState> = ScreenState.Loading
    override fun routeEvent(event: Event) {
        when (event) {
            is SettingsEvent.BackScreen -> {
                navigate.backStack()
            }

            is SettingsEvent.AddActivity -> {
                addActivity(event.activity)
            }

            is SettingsEvent.DeleteActivity -> {
                delActivity(event.activity)
            }

            is SettingsEvent.UpdateActivity -> {
                updateActivity(event.activity)
            }

            is SettingsEvent.SetColorActivity -> {
                setColorActivity(event.activity)
            }

            is SettingsEvent.UpdateSetting -> {
                updateSetting(event.setting)
            }

            is SettingsEvent.GetSetting -> {
                getSetting(event.setting)
            }

            is SettingsEvent.GetSettings -> {
                getSettings()
            }

            is SettingsEvent.StartScanBLE -> {
                startScanBle()
            }

            is SettingsEvent.StopScanBLE -> {
                stopScanBle()
            }

            is SettingsEvent.SelectDevice -> {
                selectDeviceBle(event.device)
            }

            is SettingsEvent.ClearCacheBLE -> {
                clearCacheBle()
            }

            is SettingsEvent.Init -> {
                init()
            }
        }
    }

    private fun addActivity(activity: Activity) {
        viewModelScope.launch {
            addActivity.execute(AddActivityUC.Request(activity))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }

    private fun getsActivity() {
        viewModelScope.launch {
            getsActivity.execute(GetsActivityUC.Request)
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }

    private fun delActivity(activity: Activity) {
        viewModelScope.launch {
            delActivity.execute(DeleteActivityUC.Request(activity))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }

    private fun updateActivity(activity: Activity) {
        viewModelScope.launch {
            updateActivity.execute(UpdateActivityUC.Request(activity))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }

    private fun setColorActivity(activity: Activity) {
        viewModelScope.launch {
            setColorActivity.execute(SetColorActivityUC.Request(activity))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }

    private fun updateSetting(setting: SettingRecord) {
        viewModelScope.launch {
            updateSetting.execute(UpdateSettingUC.Request(setting))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }

    private fun getSetting(setting: SettingRecord) {
        viewModelScope.launch {
            getSetting.execute(GetSettingUC.Request(setting))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }

    private fun getSettings() {
        viewModelScope.launch {
            getSettings.execute(GetSettingsUC.Request)
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }

    private fun startScanBle() {
        viewModelScope.launch {
            startScanBle.execute(StartScanBleUC.Request)
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }

    private fun stopScanBle() {
        viewModelScope.launch {
            stopScanBle.execute(StopScanBleUC.Request)
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }

    private fun clearCacheBle() {
        viewModelScope.launch {
            clearCacheBle.execute(ClearCacheBleUC.Request)
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }

    private fun selectDeviceBle(device: DeviceUI) {
        viewModelScope.launch {
            selectDeviceBle.execute(SelectDeviceBleUC.Request(device))
                .map { converter.convert(it) }.collect { submitState(it) }
        }
    }

    fun init() {
        getSettings()
        getsActivity()
    }

    fun getDevBleStored() {}
    fun getStateScanningBle() {}
    fun getStateConnectingBle() {}
    fun getHearthRate() {}
}