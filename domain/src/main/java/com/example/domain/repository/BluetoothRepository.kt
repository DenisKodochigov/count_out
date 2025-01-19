package com.example.domain.repository

interface BluetoothRepository {

    fun startScanning()
    fun stopScanning()
    fun connectDevice()
}