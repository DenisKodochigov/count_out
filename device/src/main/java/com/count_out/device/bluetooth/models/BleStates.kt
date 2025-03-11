package com.count_out.device.bluetooth.models

import com.count_out.domain.entity.enums.ErrorBleService
import com.count_out.domain.entity.enums.RunningState
import com.count_out.domain.entity.enums.StateBleConnecting
import kotlinx.coroutines.flow.MutableStateFlow

class BleStates {
    var stateBleScanner: MutableStateFlow<RunningState?> = MutableStateFlow(RunningState.Stopped)
    var stateBleConnecting: StateBleConnecting = StateBleConnecting.NONE
    var error: ErrorBleService = ErrorBleService.NONE
}