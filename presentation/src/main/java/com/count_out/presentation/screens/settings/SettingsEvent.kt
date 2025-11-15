package com.count_out.presentation.screens.settings

import com.count_out.domain.entity.Settings
import com.count_out.domain.entity.router.DeviceBle
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.presentation.models.LauncherBSp
import com.count_out.presentation.screens.prime.Event

sealed class SettingsEvent: Event {
    data object ClearCacheBLE: SettingsEvent()
    data object StartScanBLE: SettingsEvent()
    data object StopScanBLE: SettingsEvent()

    data object GetSettings: SettingsEvent()
    data class UpdateSetting(val setting: Settings): SettingsEvent()

    data class AddActivity(val activity: Activity): SettingsEvent()
    data class UpdateActivity(val activity: Activity): SettingsEvent()
    data class DeleteActivity(val activity: Activity): SettingsEvent()
    data class SetColorActivity(val activity: Activity): SettingsEvent()
    data class SetCollapsing(val item: Collapsing): SettingsEvent()
    data class SelectDevice(val device: DeviceBle): SettingsEvent()

    data class Launcher(val item: LauncherBSp): SettingsEvent()
}