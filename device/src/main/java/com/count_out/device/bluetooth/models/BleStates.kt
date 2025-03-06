package com.count_out.device.bluetooth.models

import com.count_out.domain.entity.enums.ErrorBleService
import com.count_out.domain.entity.enums.RunningState
import com.count_out.domain.entity.enums.StateBleConnecting
import kotlinx.coroutines.flow.MutableStateFlow

class BleStates {
    var stateBleScanner: MutableStateFlow<com.count_out.domain.entity.enums.RunningState?> = MutableStateFlow(
        com.count_out.domain.entity.enums.RunningState.Stopped
    )
    var stateBleConnecting: com.count_out.domain.entity.enums.StateBleConnecting =
        com.count_out.domain.entity.enums.StateBleConnecting.NONE
    var error: com.count_out.domain.entity.enums.ErrorBleService =
        com.count_out.domain.entity.enums.ErrorBleService.NONE
}