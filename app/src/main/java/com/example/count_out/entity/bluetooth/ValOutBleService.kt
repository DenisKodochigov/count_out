package com.example.count_out.entity.bluetooth

import android.bluetooth.BluetoothDevice
import com.example.count_out.entity.StateRunning
import kotlinx.coroutines.flow.MutableStateFlow

data class ValOutBleService(
    val rate: MutableStateFlow<Int> = MutableStateFlow(0),
    val stateRunning: MutableStateFlow<StateRunning> = MutableStateFlow(StateRunning.Created),
    var listDevice:  MutableStateFlow<List<BluetoothDevice>> = MutableStateFlow(emptyList()),
    var listConnection:  MutableStateFlow<List<BleConnection>> = MutableStateFlow(emptyList()),
){
    fun cancel(){
        rate.value = 0
        stateRunning.value = StateRunning.Stopped
    }
}
