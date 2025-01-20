package com.example.count_out.device.bluetooth.modules

import com.example.count_out.entity.ErrorBleService
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.StateBleConnecting
import kotlinx.coroutines.flow.MutableStateFlow

class BleStates {
    var stateBleScanner: MutableStateFlow<RunningState?> = MutableStateFlow(RunningState.Stopped)
    var stateBleConnecting: StateBleConnecting = StateBleConnecting.NONE
    var error: ErrorBleService = ErrorBleService.NONE
}