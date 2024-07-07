package com.example.count_out.entity.bluetooth

import android.bluetooth.BluetoothDevice
import com.example.count_out.entity.StateScanner
import com.example.count_out.entity.StateService
import kotlinx.coroutines.flow.MutableStateFlow

data class ValOutBleService(
    val rate: MutableStateFlow<Int> = MutableStateFlow(0),
    val stateScanner: MutableStateFlow<StateScanner> = MutableStateFlow(StateScanner.END),
    var stateService: StateService = StateService.DECLARED,
    val listDevice:  MutableStateFlow<List<BluetoothDevice>> = MutableStateFlow(emptyList()),
    var listConnection:  MutableStateFlow<List<BleConnection>> = MutableStateFlow(emptyList()),
){
    fun cancel(){
        rate.value = 0
    }
}
//
