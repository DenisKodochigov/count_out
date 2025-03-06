package com.example.count_out.ui.screens.settings

import com.example.count_out.entity.bluetooth.DeviceUI
import com.example.count_out.entity.settings.SettingRecord
import com.example.count_out.entity.workout.Activity
import com.example.count_out.ui.screens.prime.Event

sealed class SettingsEvent: Event {
    data object ClearCacheBLE: SettingsEvent()
    data object StartScanBLE: SettingsEvent()
    data object StopScanBLE: SettingsEvent()

    data object GetSettings: SettingsEvent()
    data class GetSetting(val setting: SettingRecord): SettingsEvent()
    data class UpdateSetting(val setting: SettingRecord): SettingsEvent()

    data class AddActivity(val activity: Activity): SettingsEvent()
    data class UpdateActivity(val activity: Activity): SettingsEvent()
    data class DeleteActivity(val activity: Activity): SettingsEvent()
    data class SetColorActivity(val activity: Activity): SettingsEvent()

    data class SelectDevice(val device: DeviceUI): SettingsEvent()
    data object Init : SettingsEvent()
    data object BackScreen : SettingsEvent()
}