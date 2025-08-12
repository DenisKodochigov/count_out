package com.count_out.presentation.screens.settings

import com.count_out.domain.entity.Setting
import com.count_out.domain.entity.router.DeviceUI
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.workout.Collapsing
import com.count_out.domain.entity.workout.ShowBottomSheet
import com.count_out.presentation.screens.prime.Event
import com.count_out.presentation.screens.training.TrainingEvent

sealed class SettingsEvent: Event {
    data object ClearCacheBLE: SettingsEvent()
    data object StartScanBLE: SettingsEvent()
    data object StopScanBLE: SettingsEvent()

    data object GetSettings: SettingsEvent()
//    data class GetSetting(val setting: SettingRecord): SettingsEvent()
    data class UpdateSetting(val setting: Setting): SettingsEvent()

    data class AddActivity(val activity: Activity): SettingsEvent()
    data class UpdateActivity(val activity: Activity): SettingsEvent()
    data class DeleteActivity(val activity: Activity): SettingsEvent()
    data class SetColorActivity(val activity: Activity): SettingsEvent()

    data class ShowBS(val item: ShowBottomSheet): SettingsEvent()
    data class SetCollapsing(val item: Collapsing): SettingsEvent()
    data class SelectDevice(val device: DeviceUI): SettingsEvent()
    data object Init : SettingsEvent()
    data object BackScreen : SettingsEvent()
}