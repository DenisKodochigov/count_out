package com.example.count_out.entity

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import com.example.count_out.permission.PermissionApp

@SuppressLint("MissingPermission")
fun BluetoothDevice.cached(context: Context): Boolean{
    val  permissionApp = PermissionApp(context)
    return permissionApp.checkBleScan{
        this.type == BluetoothDevice.DEVICE_TYPE_UNKNOWN
    } as Boolean
}