package com.example.count_out.entity.bluetooth

import com.example.count_out.entity.BleTask
import com.example.count_out.entity.ErrorBleService
import com.example.count_out.entity.StateBleConnecting
import com.example.count_out.entity.StateBleScanner

class BleStates {
    var task: BleTask = BleTask.NONE
    var stateBleScanner: StateBleScanner = StateBleScanner.END
    var stateBleConnecting: StateBleConnecting = StateBleConnecting.NONE
    var error: ErrorBleService = ErrorBleService.NONE
}