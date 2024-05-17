package com.example.count_out.service.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BluetoothManager @Inject constructor(val context: Context) {
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var bluetoothDevice : BluetoothDevice
    private lateinit var bluetoothGatt : BluetoothGatt

    fun onCreate(){
        bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
//        val bluetoothAvailable = PackageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)
//        val bluetoothLEAvailable = PackageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
        val bluetoothAdapter = bluetoothManager.adapter
        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }
//        val bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceAddress)
//        val bluetoothGatt = bluetoothDevice.connectGatt(this, false, gattCallback)
    }
}