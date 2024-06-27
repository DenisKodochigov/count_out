package com.example.count_out.service.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattCharacteristic.PROPERTY_READ
import android.os.Handler
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
    fun readCharacteristic(
        gatt: BluetoothGatt?,
        permissionApp: PermissionApp,
        characteristic: BluetoothGattCharacteristic?): Boolean
    {
        if (gatt == null) {
            lg( "ERROR: Gatt is 'null', ignoring read request")
            return false
        }
        if (characteristic == null) {
            lg( "ERROR: Characteristic is 'null', ignoring read request")
            return false
        }
        if ((characteristic.properties and PROPERTY_READ) == 0) {
            lg( "ERROR: Characteristic cannot be read")
            return false
        }
        // Enqueue the read command now that all checks have been passed
        val result: Boolean = commandQueue.add(
            Runnable {
                gatt?.let { gt->
                    val successReadCharacteristic: Boolean =
                        permissionApp.checkBleScan { gt.readCharacteristic(characteristic) } as Boolean
                    if (!successReadCharacteristic) {
                        lg( "ERROR: readCharacteristic failed for characteristic: ${characteristic.uuid}")
                        completedCommand( gatt )
                    } else {
                        lg( "reading characteristic ${characteristic.uuid}")
                        nrTries++
                    }
                }
            }
        )

        if (result) { nextCommand( gatt )
        } else { lg( "ERROR: Could not enqueue read characteristic command") }
        return result
    }

    private fun nextCommand(gatt: BluetoothGatt?,) {
        if (commandQueueBusy) { return }
        if (gatt == null) {
//            lg( "ERROR: GATT is 'null' for peripheral ${getAddress()}, clearing command queue")
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
//    override fun onCharacteristicRead( address: String, status: Int, handle: Int, value: List<Byte>
//    ) {
//        if (VDBG) lg("onCharacteristicRead() - Device=$address; handle=$handle; Status=$status");
//        if (!address.equals(mDevice.getAddress())) { return; }
//        synchronized(mDeviceBusy) { mDeviceBusy = false; }
//    }
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
}