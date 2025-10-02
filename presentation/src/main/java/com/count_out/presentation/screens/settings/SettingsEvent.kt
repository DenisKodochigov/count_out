package com.count_out.presentation.screens.settings

import com.count_out.domain.entity.Settings
import com.count_out.domain.entity.router.DeviceBle
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.presentation.models.ActivityImplP
import com.count_out.presentation.screens.prime.Event

sealed class SettingsEvent: Event {
    data object ClearCacheBLE: SettingsEvent()
    data object StartScanBLE: SettingsEvent()
    data object StopScanBLE: SettingsEvent()

    data object GetSettings: SettingsEvent()
//    data class GetSetting(val setting: SettingRecord): SettingsEvent()
    data class UpdateSetting(val setting: Settings): SettingsEvent()

    data class AddActivity(val activity: ActivityImplP): SettingsEvent()
    data class UpdateActivity(val activity: ActivityImplP): SettingsEvent()
    data class DeleteActivity(val activity: ActivityImplP): SettingsEvent()
    data class SetColorActivity(val activity: ActivityImplP): SettingsEvent()

    data class ShowBS(val item: ShowBottomSheet): SettingsEvent()
    data class SetCollapsing(val item: Collapsing): SettingsEvent()
    data class SelectDevice(val device: DeviceBle): SettingsEvent()
//    data object Init : SettingsEvent()
//    data object BackScreen : SettingsEvent()
}