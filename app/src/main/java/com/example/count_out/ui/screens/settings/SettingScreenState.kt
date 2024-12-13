package com.example.count_out.ui.screens.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.data.room.tables.SettingDB
import com.example.count_out.entity.ConnectState
import com.example.count_out.entity.bluetooth.DeviceUI
import com.example.count_out.entity.workout.Activity

data class SettingScreenState(
    //from viewmodel
    val settings: List<SettingDB> = emptyList(),
    val heartRate: Int = 0,
    val devicesUI: List<DeviceUI> = emptyList(),
    val lastConnectHearthRateDevice: DeviceUI? = null,
    val activities: List<Activity> = emptyList(),
    val scannedBle: Boolean = false,
    val connectingState: ConnectState = ConnectState.NOT_CONNECTED,
    //for screen
    val showBottomSheetAddActivity: MutableState<Boolean> = mutableStateOf(false),
    val showBottomSheetBLE: MutableState<Boolean> = mutableStateOf(false),
    val activity: MutableState<Activity> = mutableStateOf(ActivityDB()),
    val collapsingActivity: MutableState<Boolean> = mutableStateOf(false),

    @Stable val onSetColorActivity: (Long, Int) -> Unit = { _, _ ->},
    @Stable val onAddActivity: (Activity) ->Unit = {},
    @Stable val onUpdateActivity: (Activity) ->Unit = {},
    @Stable val onDeleteActivity: (Long) ->Unit = {},
    @Stable val onSelectDevice: ( DeviceUI ) ->Unit = {},

    @Stable val onUpdateSetting: (SettingDB) ->Unit = {},
    @Stable val onGetSettings: () ->Unit = {},
    @Stable val onClearCacheBLE: () ->Unit = {},
    @Stable val onStartScanBLE: () ->Unit = {},
    @Stable val onStopScanBLE: () ->Unit = {},

    @Stable var onDismissAddActivity: (SettingScreenState) -> Unit = { uiState->
        uiState.showBottomSheetAddActivity.value = false },
    @Stable var onConfirmAddActivity: (SettingScreenState) -> Unit = { uiState ->
        onAddActivity(uiState.activity.value)
        uiState.showBottomSheetAddActivity.value = false },
    @Stable var onDismissBLEScan: (SettingScreenState) -> Unit = {uiState ->
        onStopScanBLE()
        uiState.showBottomSheetBLE.value = false },
//    @Stable var onConfirmBLEScan: (String, SettingScreenState) -> Unit = { addr, uiState->
//        onSelectDevice(addr)
//        uiState.showBottomSheetBLE.value = false },
)