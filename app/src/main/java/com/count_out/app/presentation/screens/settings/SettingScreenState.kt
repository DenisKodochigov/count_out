package com.count_out.app.presentation.screens.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.font.FontVariation.Setting
import com.count_out.app.data.room.tables.SettingDB
import com.count_out.app.entity.ConnectState
import com.count_out.app.entity.bluetooth.DeviceUI
import com.count_out.domain.entity.Activity

import kotlin.let

data class SettingScreenState(
    //from viewmodel
    val settings: List<Setting> = emptyList(),
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

    @Stable val onSetColorActivity: (Long, Int) -> Unit = { _, _ ->},
    @Stable val onAddActivity: (Activity) ->Unit = {},
    @Stable val onUpdateActivity: (Activity) ->Unit = {},
    @Stable val onDeleteActivity: (Long) ->Unit = {},
    @Stable val onSelectDevice: (DeviceUI) ->Unit = {},

    @Stable val onUpdateSetting: (SettingDB) ->Unit = {},
    @Stable val onGetSettings: () ->Unit = {},
    @Stable val onClearCacheBLE: () ->Unit = {},
    @Stable val onStartScanBLE: () ->Unit = {},
    @Stable val onStopScanBLE: () ->Unit = {},

    @Stable var onDismissAddActivity: (SettingScreenState) -> Unit = { uiState->
        uiState.showBottomSheetAddActivity.value = false },
    @Stable var onConfirmAddActivity: (SettingScreenState) -> Unit = { uiState ->
        uiState.activity.value?.let { onAddActivity(it) }
        uiState.showBottomSheetAddActivity.value = false },
    @Stable var onDismissBLEScan: (SettingScreenState) -> Unit = {uiState ->
        onStopScanBLE()
        uiState.showBottomSheetBLE.value = false },
//    @Stable var onConfirmBLEScan: (String, SettingScreenState) -> Unit = { addr, uiState->
//        onSelectDevice(addr)
//        uiState.showBottomSheetBLE.value = false },
)