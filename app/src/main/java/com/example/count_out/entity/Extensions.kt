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


//fun <T> StateFlow<T>.logState() =
//    stateIn(CoroutineScope(Dispatchers.Default), SharingStarted.Eagerly, lg("state flow ${value.toString()}"))
//suspend fun <T> StateFlow<T>.logState1() = collectLatest { lg("state flow ${it.toString()}") }