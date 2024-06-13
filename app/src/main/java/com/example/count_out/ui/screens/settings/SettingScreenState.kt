package com.example.count_out.ui.screens.settings

import android.bluetooth.BluetoothDevice
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.example.count_out.data.room.tables.ActivityDB
import com.example.count_out.data.room.tables.SettingDB
import com.example.count_out.entity.Activity

data class SettingScreenState(
    val showBottomSheetAddActivity: MutableState<Boolean> = mutableStateOf(false),
    val showBottomSheetBLE: MutableState<Boolean> = mutableStateOf(false),
    val settings: MutableState<List<SettingDB>> = mutableStateOf(emptyList()),
    val bluetoothDevices: MutableState<List<BluetoothDevice>> = mutableStateOf(emptyList()),
    val collapsingActivity: MutableState<Boolean> = mutableStateOf(false),
    val activities: List<Activity> = emptyList(),
    val lastConnectHearthRate: MutableState<BluetoothDevice?> = mutableStateOf(value = null),
    val activity: MutableState<Activity> = mutableStateOf(ActivityDB()),

    @Stable val onSetColorActivity: (Long, Int) -> Unit = { _, _ ->},
    @Stable val onAddActivity: (Activity) ->Unit = {},
    @Stable val onUpdateActivity: (Activity) ->Unit = {},
    @Stable val onDeleteActivity: (Long) ->Unit = {},
    @Stable val onSelectDevice: (BluetoothDevice) ->Unit = {},

    @Stable val onUpdateSetting: (SettingDB) ->Unit = {},
    @Stable val onGetSettings: () ->Unit = {},
    @Stable val onClearCacheBLE: () ->Unit = {},
    @Stable val onStartScanBLE: () ->Unit = {},
    @Stable val onStopScanBLE: () ->Unit = {},

    @Stable var onDismissAddActivity: () -> Unit = {},
    @Stable var onConfirmAddActivity: (Activity) -> Unit = {},
    @Stable var onDismissBLEScan: () -> Unit = {},
    @Stable var onConfirmBLEScan: (BluetoothDevice) -> Unit = {},
)