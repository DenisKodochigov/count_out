package com.example.count_out.entity.bluetooth

import android.bluetooth.BluetoothDevice

data class ListConnection(
    val listConnection: MutableList<BleConnection> = mutableListOf()
){
    fun findConnection(address: String): BleConnection? = listConnection.find{ it.address == address}
    fun addConnection(device: BluetoothDevice):BleConnection {
        var connection = listConnection.find{ it.address == device.address}
        if ( connection == null) {
            listConnection.add( BleConnection(device = device))
            connection = listConnection.last()
        }
        return connection
    }
}
