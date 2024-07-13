package com.example.count_out.entity.bluetooth

import com.example.count_out.entity.ErrorBleService
import com.example.count_out.entity.StateScanner
import com.example.count_out.entity.StateService

class BleStates {
    var stateScanner: StateScanner = StateScanner.END
    var stateService: StateService = StateService.DECLARED
    var error: ErrorBleService = ErrorBleService.NONE
}