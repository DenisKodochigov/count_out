package com.example.count_out.service.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.os.Handler
import com.example.count_out.entity.UUIDBle
import com.example.count_out.permission.PermissionApp
import com.example.count_out.ui.view_components.lg
import java.util.LinkedList
import java.util.Queue


class BleQueueCommand {
    private val commandQueue: Queue<Runnable> = LinkedList(emptyList())
    private var commandQueueBusy = false
    private var isRetrying = false
    private var nrTries = 0
    private val MAX_TRIES = 4
    private val bleHandler = Handler()

    @SuppressLint("MissingPermission")
    fun readCharacteristic(gatt: BluetoothGatt?, permissionApp: PermissionApp, uuidBle: UUIDBle): Boolean
    {
        var result = false
        gatt?.let { gt ->
            result = commandQueue.add( Runnable { readHeartRate(permissionApp, gt, uuidBle) })
            if (result) { nextCommand(gatt) }
            else { lg("ERROR: Could not enqueue read characteristic command") }
        } ?: lg( "ERROR: Gatt is 'null', ignoring read request")
        return result
    }

    @SuppressLint("MissingPermission")
    private fun readHeartRate(permissionApp: PermissionApp, gatt: BluetoothGatt, uuidBle: UUIDBle) {
        val characteristic = gatt.getService(uuidBle.serviceUuid)?.getCharacteristic(uuidBle.charUuid)
        if (characteristic?.isReadable() == true) {
            permissionApp.checkBleScan { gatt.readCharacteristic(characteristic)}
            lg( "reading characteristic ${characteristic?.uuid}")
            nrTries++
        } else {
            lg( "ERROR: readCharacteristic failed for characteristic: ${characteristic?.uuid}")
            completedCommand( gatt )
        }
    }

    private fun nextCommand(gatt: BluetoothGatt?,) {
        if (commandQueueBusy) { return }
        if (gatt == null) {
            lg( "ERROR: GATT is 'null' for peripheral, clearing command queue")
            commandQueue.clear()
            commandQueueBusy = false
            return
        }
        if (commandQueue.size > 0) {
            val bluetoothCommand: Runnable? = commandQueue.peek()
            commandQueueBusy = true
            nrTries = 0

            bleHandler.post {
                try {
                    bluetoothCommand?.run()
                } catch (ex: Exception) {
                    lg("ERROR: Command exception for device getName(), $ex") }
            }
        }
    }

    fun completedCommand(gatt: BluetoothGatt?,) {
        commandQueueBusy = false
        isRetrying = false
        commandQueue.poll()
        nextCommand(gatt)
    }

    private fun retryCommand(gatt: BluetoothGatt?) {
        commandQueueBusy = false
        val currentCommand: Runnable? = commandQueue.peek()
        if (currentCommand != null) {
            if (nrTries >= MAX_TRIES) {
                lg( "Max number of tries reached")
                commandQueue.poll()
            } else {
                isRetrying = true
            }
        }
        nextCommand(gatt)
    }

    @SuppressLint("MissingPermission")
    private fun getName(device: BluetoothDevice?,  permissionApp: PermissionApp): String {
        return device?.let{ permissionApp.checkBleScan { it.name } as String } ?: ""
    }
    private fun getAddress(device: BluetoothDevice?,  permissionApp: PermissionApp): String {
        return device?.let{ permissionApp.checkBleScan { it.address } as String } ?: ""
    }

    fun BluetoothGattCharacteristic.isReadable(): Boolean =
        containsProperty(BluetoothGattCharacteristic.PROPERTY_READ)
    fun BluetoothGattCharacteristic.isWritable(): Boolean =
        containsProperty(BluetoothGattCharacteristic.PROPERTY_WRITE)
    fun BluetoothGattCharacteristic.isWritableWithoutResponse(): Boolean =
        containsProperty(BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)
    fun BluetoothGattCharacteristic.containsProperty(property: Int): Boolean {
        return properties and property != 0
    }
}