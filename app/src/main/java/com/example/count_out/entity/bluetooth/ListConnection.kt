package com.example.count_out.entity.bluetooth

import android.bluetooth.BluetoothDevice

data class ListConnection(
    val listConnection: MutableList<BleConnection> = mutableListOf()
){
    fun findConnection(device: BluetoothDevice): BleConnection
    {
        var connection: BleConnection? = listConnection.find{ it.address == device.address}
        if (connection == null){
            connection = BleConnection(device = device)
            listConnection.add(connection)
        }
        return connection
    }
}
