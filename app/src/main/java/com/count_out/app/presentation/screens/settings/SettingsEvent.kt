package com.count_out.app.presentation.screens.settings

import com.count_out.app.presentation.prime.Event
import com.count_out.domain.entity.SettingRecord
import com.count_out.entity.entity.router.DeviceUI
import com.count_out.entity.entity.workout.Activity

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