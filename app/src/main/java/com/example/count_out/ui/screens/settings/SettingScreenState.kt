package com.example.count_out.ui.screens.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.data.room.tables.SettingDB
import com.example.count_out.entity.Activity
import com.example.count_out.entity.bluetooth.DeviceUI

data class SettingScreenState(
    //from viewmodel
    val settings: List<SettingDB> = emptyList(),
    val heartRate: Int = 0,
    val devicesUI: List<DeviceUI> = emptyList(),
    val lastConnectHearthRateDevice: DeviceUI? = null,
    val activities: List<Activity> = emptyList(),
    val scannedBle: Boolean = false,
    val connectingDevice: Boolean = false,
    //for screen
    val showBottomSheetAddActivity: MutableState<Boolean> = mutableStateOf(false),
    val showBottomSheetBLE: MutableState<Boolean> = mutableStateOf(false),
    val activity: MutableState<Activity> = mutableStateOf(ActivityDB()),
    val collapsingActivity: MutableState<Boolean> = mutableStateOf(false),

    @Stable val onSetColorActivity: (Long, Int) -> Unit = { _, _ ->},
    @Stable val onAddActivity: (Activity) ->Unit = {},
    @Stable val onUpdateActivity: (Activity) ->Unit = {},
    @Stable val onDeleteActivity: (Long) ->Unit = {},
    @Stable val onSelectDevice: (String) ->Unit = {},

    @Stable val onUpdateSetting: (SettingDB) ->Unit = {},
    @Stable val onGetSettings: () ->Unit = {},
    @Stable val onClearCacheBLE: () ->Unit = {},
    @Stable val onStartScanBLE: () ->Unit = {},
    @Stable val onStopScanBLE: () ->Unit = {},

    @Stable var onDismissAddActivity: () -> Unit = {},
    @Stable var onConfirmAddActivity: (Activity) -> Unit = {},
    @Stable var onDismissBLEScan: () -> Unit = {},
    @Stable var onConfirmBLEScan: (String) -> Unit = {},
)