package com.example.count_out.service.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGatt.GATT_FAILURE
import android.bluetooth.BluetoothGatt.GATT_SUCCESS
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothStatusCodes
import android.content.Context
import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_DATA
import android.os.Build
import com.example.count_out.entity.Const.UUID_HEART_RATE_MEASUREMENT
import com.example.count_out.entity.ErrorBleService
import com.example.count_out.entity.bluetooth.BleConnection
import com.example.count_out.entity.bluetooth.ListConnection
import com.example.count_out.entity.bluetooth.UUIDBle
import com.example.count_out.entity.bluetooth.ValInBleService
import com.example.count_out.entity.bluetooth.ValOutBleService
import com.example.count_out.entity.hciStatusFromValue
import com.example.count_out.permission.PermissionApp
import com.example.count_out.ui.view_components.lg
import javax.inject.Inject


class BleConnecting @Inject constructor(
    val context: Context, private val permissionApp: PermissionApp,
) {
    private val bleQueue = BleQueue()
    private val listConnection = ListConnection()
    private lateinit var connection: BleConnection

    fun connectDevice(valIn: ValInBleService, valOut: ValOutBleService) {
        valIn.device.value.device?.let { device->
            bleQueue.addCommandInQueue { commandConnectingGatt(device, valOut) }
            bleQueue.addCommandInQueue { commandStartDiscoverService(valOut) }
        }
    }

    @SuppressLint("MissingPermission")
    fun commandConnectingGatt(device: BluetoothDevice, valOut: ValOutBleService): Boolean{
        connection = listConnection.findConnection(device)
        return if ( permissionApp.checkBleScan { connectGattAutoConnect(device) } as Boolean){ true }
                else{
                    valOut.error.value = ErrorBleService.CONNECT_GATT
                    false
                }
    }

    @SuppressLint("MissingPermission")
    fun connectGattAutoConnect(device: BluetoothDevice): Boolean {
        connection.device = device
        connection.gatt = device.connectGatt(context,true, bluetoothGattCallback, BluetoothDevice.TRANSPORT_LE)
        return connection.gatt != null
    }

    @SuppressLint("MissingPermission")
    fun commandStartDiscoverService(valOut: ValOutBleService): Boolean{
        var result = false
        if ( connection.newState.value == BluetoothGatt.STATE_CONNECTED) {
            connection.gatt?.let {
                if (startDiscoverService(it)) result = true
                else valOut.error.value = ErrorBleService.DISCOVER_SERVICE
            }
        }
        return result
    }

    @SuppressLint("MissingPermission")
    private fun startDiscoverService(gatt: BluetoothGatt): Boolean {
        return  if (gatt.discoverServices()) { gatt.printCharacteristicsTable(); true }
        else { lg("discoverServices failed to start"); false }
    }

    @SuppressLint("MissingPermission")
    fun setCharacteristicNotification(): Boolean {
        lg("setCharacteristicNotification")
        val enable =true
        var result = false
        connection.characteristic?.let{ charact->
            connection.gatt?.setCharacteristicNotification(charact, enable)
            val descriptor = charact.getDescriptor(connection.descriptorUuid)

            result = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
                connection.gatt?.writeDescriptor(descriptor, byteArrayOf(0x00, 0x00)) == BluetoothStatusCodes.SUCCESS
            } else {
                descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                connection.gatt?.writeDescriptor(descriptor) ?: false
            }
        }
        return result //descriptor write operation successfully started?
    }

    private val bluetoothGattCallback = object: BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int)
        {
            super.onConnectionStateChange(gatt, status, newState)
            lg("onConnectionStateChange status $status; newState: $newState")
            bluetoothGattCallbackConnectionStateChange(gatt, status, newState)
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int)
        {
            super.onServicesDiscovered(gatt, status)
            bluetoothGattCallbackServicesDiscovered(gatt, status)
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray,
            status: Int,
        ){
            super.onCharacteristicRead(gatt, characteristic, value, status)
            bluetoothGattCallbackCharacteristicRead(status, value, characteristic)
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray
        ) {
            super.onCharacteristicChanged(gatt, characteristic, value)
            if (connection.characteristic == characteristic)
                connection.valueCharacteristic.value = value
        }
    }

    @SuppressLint("MissingPermission")
    fun bluetoothGattCallbackConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int,
    ) {
        connection.connectStatus.value = status
        connection.newState.value = newState
        if (status == GATT_SUCCESS) {
            when (newState) {
                BluetoothGatt.STATE_CONNECTED -> {
                    lg("Connection state Bluetooth Gatt STATE_CONNECTED")
//                    broadcastUpdate(ACTION_GATT_CONNECTED)
                }
                BluetoothGatt.STATE_DISCONNECTED -> {
                    lg("Connection state Bluetooth Gatt STATE_DISCONNECTED")
                    bluetoothGattCallbackGattClose(gatt)
                }
                else -> { lg("Connection state Bluetooth Gatt GATT_SUCCESS") }
            }
        } else {
            lg("BluetoothGatt status: $status (${hciStatusFromValue(status)})")
            bluetoothGattCallbackGattClose(gatt)
        }
    }

    @SuppressLint("MissingPermission")
    fun bluetoothGattCallbackServicesDiscovered(gatt: BluetoothGatt?, status: Int)
    {
        connection.connectStatus.value = status
        if (status == GATT_SUCCESS) {
            lg("Discovered ${gatt?.services?.size ?: 0} services")
        } else {
            lg("Service discovery failed")
            if (status == GATT_FAILURE) permissionApp.checkBleScan { gatt!!.disconnect() }
        }
    }

    fun bluetoothGattCallbackCharacteristicRead(
        status: Int,
        value: ByteArray,
        characteristic: BluetoothGattCharacteristic,
    ) {
        connection.connectStatus.value = status
        if (status != GATT_SUCCESS) {
            lg("ERROR: Read failed for characteristic: ${characteristic.uuid}, status $status")
            bleQueue.completedCommand();
            return;
        }
        // Characteristic has been read so processes it
        //...
        when (characteristic.uuid) {
            UUID_HEART_RATE_MEASUREMENT -> {
                val flag = characteristic.properties
                val format = when (flag and 0x01) {
                    0x01 -> {
                        lg("Heart rate format UINT16.")
                        BluetoothGattCharacteristic.FORMAT_UINT16
                    }
                    else -> {
                        lg( "Heart rate format UINT8.")
                        BluetoothGattCharacteristic.FORMAT_UINT8
                    }
                }
                val heartRate = characteristic.getIntValue(format, 1)
                lg( "Received heart rate: $heartRate")
//                intent.putExtra(EXTRA_DATA, (heartRate).toString())
            }
            else -> {
                // For all other profiles, writes the data formatted in HEX.
                val data: ByteArray? = characteristic.value
                if (data?.isNotEmpty() == true) {
                    val hexString: String = data.joinToString(separator = " ") {
                        String.format("%02X", it)
                    }
//                    intent.putExtra(EXTRA_DATA, "$data\n$hexString")
                }
            }
        }
        // We done, complete the command
        bleQueue.completedCommand();
    }


    @SuppressLint("MissingPermission")
    fun disconnectDevice() {
        connection.gatt?.let { permissionApp.checkBleScan {
            it.disconnect()
            it.close()
        } }
    }

    fun clearServicesCache(): Boolean {
        var result = false
        connection.gatt?.let { gattL ->
            try {
                val refreshMethod = gattL.javaClass.getMethod("refresh")
                result = refreshMethod.invoke(connection.gatt) as Boolean
            } catch (e: Exception) {
                lg("ERROR: Could not invoke refresh method: $e")
            }
        }
        return result
    }

    @SuppressLint("MissingPermission")
    private fun bluetoothGattCallbackGattClose(gatt: BluetoothGatt?) {
//        runBlocking { onCancelDiscoverService() }
        gattCansel(gatt)
    }

//    private suspend fun onCancelDiscoverService() {
//        jobDiscoverService.cancel()
//        while(jobReadBleCharacteristic.isCancelled){ delay(DELAY_CANCELED_COROUTINE)}
//    }

//    private suspend fun onCancelReadBleCharacteristic() {
//        jobReadBleCharacteristic.cancel()
//        while(jobReadBleCharacteristic.isCancelled) { delay(DELAY_CANCELED_COROUTINE)}
//    }

    @SuppressLint("MissingPermission")
    private fun gattCansel(gatt: BluetoothGatt?) {
        gatt?.let { permissionApp.checkBleScan { it.close() } }
    }

    fun readParameterForBle(uuidBle: UUIDBle) {
//        jobReadBleCharacteristic = CoroutineScope(Dispatchers.Default).launch {
            bleQueue.addCommandInQueue { readCharacteristicFun(permissionApp, uuidBle) }
//        }.also {
//            it.invokeOnCompletion { error ->
//                when ( error ){
//                    is CancellationException -> { lg("ERROR: $error") }
//                    is RuntimeException -> { lg("ERROR: $error") }
//                    else -> { lg("ERROR: $error") }
//                }
//            }
//        }
    }

    @SuppressLint("MissingPermission")
    private fun readCharacteristicFun(permissionApp: PermissionApp, uuidBle: UUIDBle,
    ): Boolean {
        var result = false
        connection.gatt?.let { gt ->
            val characteristic = gt.getService(uuidBle.serviceUuid)?.getCharacteristic(uuidBle.charUuid)
            if (characteristic?.isReadable() == true) {
                permissionApp.checkBleScan { gt.readCharacteristic(characteristic) }
                result = true
                lg("reading characteristic ${characteristic.uuid}")
            } else {
                lg("ERROR: readCharacteristic failed for characteristic: ${characteristic?.uuid}")
            }
        } ?: lg("ERROR: Gatt is 'null', ignoring read request")
        return result
    }

    @SuppressLint("MissingPermission")
    fun bleConnectCancel() {
//        runBlocking {
//            onCancelReadBleCharacteristic()
            disconnectDevice()
//            onCancelDiscoverService()
            gattCansel( connection.gatt )
//        }
    }

    private fun broadcastUpdate(action: String, characteristic: BluetoothGattCharacteristic) {
        val intent = Intent(action)

        // This is special handling for the Heart Rate Measurement profile. Data
        // parsing is carried out as per profile specifications.
        when (characteristic.uuid) {
            UUID_HEART_RATE_MEASUREMENT -> {
                val flag = characteristic.properties
                val format = when (flag and 0x01) {
                    0x01 -> {
                        lg( "Heart rate format UINT16.")
                        BluetoothGattCharacteristic.FORMAT_UINT16
                    }
                    else -> {
                        lg(  "Heart rate format UINT8.")
                        BluetoothGattCharacteristic.FORMAT_UINT8
                    }
                }
                val heartRate = characteristic.getIntValue(format, 1)
                lg( String.format("Received heart rate: %d", heartRate))
                intent.putExtra(EXTRA_DATA, (heartRate).toString())
            }
            else -> {
                // For all other profiles, writes the data formatted in HEX.
                val data: ByteArray? = characteristic.value
                if (data?.isNotEmpty() == true) {
                    val hexString: String = data.joinToString(separator = " ") {
                        String.format("%02X", it)
                    }
                    intent.putExtra(EXTRA_DATA, "$data\n$hexString")
                }
            }
        }
//        sendBroadcast(intent)
    }


}