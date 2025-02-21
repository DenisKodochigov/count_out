package com.count_out.app.device.bluetooth.modules

import com.count_out.app.entity.ErrorBleService
import com.count_out.app.entity.RunningState
import com.count_out.app.entity.StateBleConnecting
import kotlinx.coroutines.flow.MutableStateFlow

class BleStates {
    var stateBleScanner: MutableStateFlow<RunningState?> = MutableStateFlow(RunningState.Stopped)
    var stateBleConnecting: StateBleConnecting = StateBleConnecting.NONE
    var error: ErrorBleService = ErrorBleService.NONE
}