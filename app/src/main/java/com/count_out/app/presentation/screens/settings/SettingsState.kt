package com.count_out.app.presentation.screens.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.count_out.domain.entity.Settings
import com.count_out.domain.entity.router.DeviceUI
import com.count_out.domain.entity.workout.Activity
import com.count_out.domain.entity.enums.ConnectState

data class SettingsState(
    val settings: Settings? = null,
    val heartRate: Int = 0,
    val devicesUI: List<DeviceUI> = emptyList(),
    val lastConnectHearthRateDevice: DeviceUI? = null,
    val activities: List<Activity> = emptyList(),
    val scannedBle: Boolean = false,
    val connectingState: ConnectState = ConnectState.NOT_CONNECTED,
    //for screen
    val showBottomSheetAddActivity: MutableState<Boolean> = mutableStateOf(false),
    val showBottomSheetBLE: MutableState<Boolean> = mutableStateOf(false),
    val activity: MutableState<Activity?> = mutableStateOf(null),
    val collapsingActivity: MutableState<Boolean> = mutableStateOf(false),

    @Stable var onDismissAddActivity: (SettingsState) -> Unit = { uiState->
        uiState.showBottomSheetAddActivity.value = false },
    @Stable var onConfirmAddActivity: (SettingsState) -> Unit = { uiState ->
//        uiState.activity.value?.let { onAddActivity(it) }
        uiState.showBottomSheetAddActivity.value = false },
    @Stable var onDismissBLEScan: (SettingsState) -> Unit = { uiState ->
//        onStopScanBLE()
        uiState.showBottomSheetBLE.value = false },
)
