package com.example.count_out.entity

import android.Manifest.permission.BLUETOOTH_SCAN
import android.bluetooth.BluetoothDevice
import android.content.Context
import com.example.count_out.permission.checkPermission

fun BluetoothDevice.cached(context: Context): Boolean{
    return checkPermission (context, BLUETOOTH_SCAN, requiredBuild = 31){
        this.type == BluetoothDevice.DEVICE_TYPE_UNKNOWN
    } as Boolean
}