package com.count_out.app.device.bluetooth.modules

import com.count_out.domain.entity.bluetooth.ErrorBleService
import com.count_out.domain.entity.bluetooth.StateBleConnecting
import com.count_out.domain.entity.enums.RunningState
import kotlinx.coroutines.flow.MutableStateFlow

//class BleStates {
//    var stateBleScanner: MutableStateFlow<RunningState?> = MutableStateFlow(RunningState.Stopped)
//    var stateBleConnecting: StateBleConnecting = StateBleConnecting.NONE
//    var error: ErrorBleService = ErrorBleService.NONE
//}