package com.example.count_out.service.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGatt.GATT_FAILURE
import android.bluetooth.BluetoothGatt.GATT_SUCCESS
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor.ENABLE_INDICATION_VALUE
import android.bluetooth.BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
import android.content.Context
import android.os.Build
import com.example.count_out.entity.Const.GattAttributes
import com.example.count_out.entity.ErrorBleService
import com.example.count_out.entity.StateService
import com.example.count_out.entity.bluetooth.BleConnection
import com.example.count_out.entity.bluetooth.BleStates
import com.example.count_out.entity.bluetooth.ReceiveFromUI
import com.example.count_out.entity.bluetooth.SendToUI
import com.example.count_out.entity.bluetooth.UUIDBle
import com.example.count_out.entity.hciStatusFromValue
import com.example.count_out.permission.PermissionApp
import com.example.count_out.ui.view_components.lg
import java.util.UUID
import javax.inject.Inject


class BleConnecting @Inject constructor(
    val context: Context, private val permissionApp: PermissionApp,
) {
//    private val bleQueue = BleQueue()
    private var connection = BleConnection()
    private val UUID_HEART_RATE_MEASUREMENT: UUID = UUID.fromString(GattAttributes.HEART_RATE_MEASUREMENT)
    private val UUID_CLIENT_CHARACTERISTIC_CONFIG: UUID =
        UUID.fromString(GattAttributes.CLIENT_CHARACTERISTIC_CONFIG)

/*################################################################################################ */
    fun connectDevice(bleStates: BleStates, receiveFromUI: ReceiveFromUI, sendToUi: SendToUI){
        receiveFromUI.currentConnection?.let {
            connection = it
            connectingGatt( bleStates )
        }
    }

    @SuppressLint("MissingPermission")
    fun connectingGatt(bleStates: BleStates): Boolean{
        var result = false
        if (bleStates.stateService == StateService.GET_REMOTE_DEVICE) {
            connection.device?.let { dev->
                connection.gatt = dev.connectGatt(
                    context, true, bluetoothGattCallback, BluetoothDevice.TRANSPORT_LE)
                if ( connection.gatt != null) {
                    result = true
                    bleStates.stateService = StateService.CONNECT_GAT
                }
                else bleStates.error = ErrorBleService.NOT_CONNECT_GATT
            }
        }
        return result
    }

    private val bluetoothGattCallback = object: BluetoothGattCallback() {
        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int)
        {
            super.onConnectionStateChange(gatt, status, newState)
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
            bluetoothGattCallbackCharacteristicRead( status, value, characteristic)
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray
        ) {
            super.onCharacteristicChanged(gatt, characteristic, value)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bluetoothGattCallbackCharacteristicChanged( value, characteristic) }
        }
        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
        ) {
            super.onCharacteristicChanged(gatt, characteristic)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                bluetoothGattCallbackCharacteristicChanged( ByteArray(0), characteristic)}
        }
    }

    @SuppressLint("MissingPermission")
    fun bluetoothGattCallbackConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int,
    ){
        connection.gattStatus.value = status
        connection.newState.value = newState
        if (status == GATT_SUCCESS) {
            when (newState) {
                BluetoothGatt.STATE_CONNECTED -> { gatt?.let { it.discoverServices() } }
                BluetoothGatt.STATE_DISCONNECTED -> {
                    lg("Connection state Bluetooth Gatt STATE_DISCONNECTED")
                    bluetoothGattCallbackGattClose()
                }
                else -> { lg("Connection state Bluetooth Gatt GATT_SUCCESS") }
            }
        } else {
            lg("BluetoothGatt status: $status (${hciStatusFromValue(status)})")
            bluetoothGattCallbackGattClose()
        }
    }

    @SuppressLint("MissingPermission")
    fun bluetoothGattCallbackServicesDiscovered(gatt: BluetoothGatt?, status: Int)
    {
        connection.gattStatus.value = status
        if (status == GATT_SUCCESS) {
            gatt?.let {
                it.printCharacteristicsTable()
                setCharacteristicNotification(it, UUID_HEART_RATE_MEASUREMENT, true)
            }
        } else {
            lg("Service discovery failed")
            connection.error.value = ErrorBleService.DISCOVER_SERVICE
            if (status == GATT_FAILURE) permissionApp.checkBleScan { gatt!!.disconnect() }
        }
    }

    fun bluetoothGattCallbackCharacteristicRead(
        status: Int, value: ByteArray, characteristic: BluetoothGattCharacteristic, ) {
        connection.gattStatus.value = status
        if (status != GATT_SUCCESS) {
            lg("ERROR: Read failed for characteristic: ${characteristic.uuid}, status $status")
//            bleQueue.completedCommand();
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
                val heartRate = value   //.getIntValue(format, 1)
                lg( "Received heart rate: $heartRate")
//                intent.putExtra(EXTRA_DATA, (heartRate).toString())
            }
            else -> {
                // For all other profiles, writes the data formatted in HEX.
                val data: ByteArray? = value
                if (data?.isNotEmpty() == true) {
                    val hexString: String = data.joinToString(separator = " ") {
                        String.format("%02X", it)
                    }
//                    intent.putExtra(EXTRA_DATA, "$data\n$hexString")
                }
            }
        }
        // We done, complete the command
//        bleQueue.completedCommand();
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun bluetoothGattCallbackCharacteristicChanged(
        value: ByteArray, characteristic: BluetoothGattCharacteristic,
    ) {
        when (characteristic.uuid) {
            UUID_HEART_RATE_MEASUREMENT -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    logCharacteristicValue(value, "${Build.VERSION.SDK_INT} Received heart rate: ")
                } else {
                    logCharacteristicValue(characteristic.value, "${Build.VERSION.SDK_INT} Received heart rate: ")}
            }
            else -> {
                logCharacteristicValue(characteristic.value, "${Build.VERSION.SDK_INT} Received heart rate: ")
            }
        }
    }

    private fun logCharacteristicValue(value: ByteArray, text: String){
        if (value.isNotEmpty()) {
            val hexString: String = value.joinToString(separator = " ") { String.format("%03d", it) }
            //            lg("$text $hexString $numericalValue")
            lg(" Pulse ${value[1].dec()}")
        }
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
    private fun bluetoothGattCallbackGattClose() {
        disconnectDevice()
    }

    @SuppressLint("MissingPermission")
    fun disconnectDevice() {
        connection.gatt?.let { permissionApp.checkBleScan {
            it.disconnect()
            it.close()
        } }
    }

    @SuppressLint("MissingPermission")
    fun setCharacteristicNotification(gatt: BluetoothGatt, uuid: UUID, enabled: Boolean)
    {
        lg("setCharacteristicNotification ")
        gatt.findCharacteristic(uuid)?.let{ characteristic->
            lg("setCharacteristicNotification $characteristic")
            gatt.setCharacteristicNotification(characteristic, enabled)
            val descriptor = characteristic.getDescriptor(UUID_CLIENT_CHARACTERISTIC_CONFIG)
            val descriptorValue = if (characteristic.isNotify()) { ENABLE_NOTIFICATION_VALUE
            } else { ENABLE_INDICATION_VALUE }
            lg("setCharacteristicNotification ${Build.VERSION.SDK_INT} descriptor: $descriptor")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                gatt.writeDescriptor(descriptor, descriptorValue)
            } else {
                descriptor.value = descriptorValue
                gatt.writeDescriptor(descriptor)
            }
        }
    }

    fun readParameterForBle(uuidBle: UUIDBle) {
        commandReadCharacteristic(permissionApp, uuidBle)
    }
    @SuppressLint("MissingPermission")
    private fun commandReadCharacteristic(permissionApp: PermissionApp, uuidBle: UUIDBle,
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

//    private suspend fun onCancelReadBleCharacteristic() {
//        jobReadBleCharacteristic.cancel()
//        while(jobReadBleCharacteristic.isCancelled) { delay(DELAY_CANCELED_COROUTINE)}
//    }
//    private fun broadcastUpdate(action: String, characteristic: BluetoothGattCharacteristic) {
//        val intent = Intent(action)

        // This is special handling for the Heart Rate Measurement profile. Data
        // parsing is carried out as per profile specifications.
//        when (characteristic.uuid) {
//            UUID_HEART_RATE_MEASUREMENT -> {
//                val flag = characteristic.properties
//                val format = when (flag and 0x01) {
//                    0x01 -> {
//                        lg( "Heart rate format UINT16.")
//                        BluetoothGattCharacteristic.FORMAT_UINT16
//                    }
//                    else -> {
//                        lg(  "Heart rate format UINT8.")
//                        BluetoothGattCharacteristic.FORMAT_UINT8
//                    }
//                }
//                val heartRate = characteristic.getIntValue(format, 1)
//                lg( String.format("Received heart rate: %d", heartRate))
//                intent.putExtra(EXTRA_DATA, (heartRate).toString())
//            }
//            else -> {
//                // For all other profiles, writes the data formatted in HEX.
//                val data: ByteArray? = characteristic.value
//                if (data?.isNotEmpty() == true) {
//                    val hexString: String = data.joinToString(separator = " ") {
//                        String.format("%02X", it)
//                    }
//                    intent.putExtra(EXTRA_DATA, "$data\n$hexString")
//                }
//            }
//        }
//        sendBroadcast(intent)
//    }


    //    fun connectDevice(valIn: ValInBleService, valOut: ValOutBleService) {
//        bleQueue.addCommandInQueue { commandConnectingGatt(valIn.bleDevice.device, valOut) }
//        bleQueue.addCommandInQueue { commandStartDiscoverService(valOut) }
//    }

//    @SuppressLint("MissingPermission")
//    fun commandConnectingDevice(device: BluetoothDevice?, valOut: ValOutBleService): Boolean{
//        var result = false
//        device?.let { dev->
//            listConnection.findConnection(dev.address)?.let { connection->
//                if ( permissionApp.checkBleScan { connectGattAutoConnect(dev) } as Boolean){ true }
//                else{
//                    valOut.error.value = ErrorBleService.CONNECT_GATT
//                    false
//                }
//            }
//        }
//        return result
//    }
//    fun getRemoteDevice( commandGetRemoteDevice: ()-> Boolean){
//        bleQueue.addCommandInQueue { commandGetRemoteDevice() }
//    }
//
//    fun connectingGatt(receiveFromUI: ReceiveFromUI, bleStates: BleStates){
//        bleQueue.addCommandInQueue {
//            commandConnectingGatt(receiveFromUI.currentConnection!!, bleStates) }
//    }
//    fun startDiscoverService(receiveFromUI: ReceiveFromUI, bleStates: BleStates){
//        bleQueue.addCommandInQueue { commandStartDiscoverService(receiveFromUI.currentConnection!!, bleStates) }
//    }
//
//    @SuppressLint("MissingPermission")
//    fun commandConnectingGatt(connection: BleConnection, bleStates: BleStates): Boolean{
//        var result = false
//        if (bleStates.stateService == StateService.GET_REMOTE_DEVICE) {
//            lg("commandConnectingGatt")
//            connection.device?.let { dev->
//                connection.gatt = dev.connectGatt(
//                    context, true, bluetoothGattCallback, BluetoothDevice.TRANSPORT_LE)
//                if ( connection.gatt != null) {
//                    result = true
//                    bleStates.stateService = StateService.CONNECT_GAT
//                }
//                else bleStates.error = ErrorBleService.NOT_CONNECT_GATT
//            }
//        }
//        return result
//    }
//
//    @SuppressLint("MissingPermission")
//    fun commandStartDiscoverService(connection: BleConnection, bleStates: BleStates): Boolean{
//        var result = false
//        lg("commandStartDiscoverService status: ${bleStates.stateService}, newState: ${connection.newState.value}")
//        timer.changeState(TimerState.COUNTING, 60000L)
//        while (timer.state.value != TimerState.END) {
//            if (bleStates.stateService == StateService.CONNECT_GAT &&
//                connection.newState.value == BluetoothGatt.STATE_CONNECTED) {
//                connection.gatt?.let {
//                    if (it.discoverServices()) {
//                        it.printCharacteristicsTable()
//                        bleStates.stateService = StateService.GET_DISCOVER_SERVICE
//                        result = true
//                    }
//                    else {
//                        bleStates.error = ErrorBleService.DISCOVER_SERVICE
//                        lg("discoverServices failed to start")
//                    }
//                }
//            }
//        }
//        return result
//    }
}