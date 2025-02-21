package com.count_out.domain.repository

interface BluetoothRepository {

    fun startScanning()
    fun stopScanning()
    fun connectDevice()
}