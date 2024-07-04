package com.example.count_out.entity.bluetooth

data class ListConnection(
    val listConnection: MutableList<BleConnection> = mutableListOf()
){
    fun findConnection(address: String): BleConnection?
    {
        return listConnection.find{ it.address == address}
    }

    fun add(bleConnection: BleConnection)
    {
        if (findConnection(bleConnection.address) == null) {
            listConnection.add(bleConnection)
        }
    }
}
