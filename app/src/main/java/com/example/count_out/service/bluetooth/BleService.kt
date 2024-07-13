package com.example.count_out.service.bluetooth

import android.annotation.SuppressLint
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE
import android.os.Binder
import android.os.Build
import android.os.IBinder
import com.example.count_out.entity.Const.NOTIFICATION_ID
import com.example.count_out.entity.Const.uuidHeartRate
import com.example.count_out.entity.ErrorBleService
import com.example.count_out.entity.StateScanner
import com.example.count_out.entity.StateService
import com.example.count_out.entity.bluetooth.BleStates
import com.example.count_out.entity.bluetooth.DeviceUI
import com.example.count_out.entity.bluetooth.ListConnection
import com.example.count_out.entity.bluetooth.ReceiveFromUI
import com.example.count_out.entity.bluetooth.SendToUI
import com.example.count_out.helpers.NotificationHelper
import com.example.count_out.ui.view_components.lg
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@AndroidEntryPoint
class BleService @Inject constructor(): Service() {

    @Inject lateinit var bleScanner: BleScanner
    @Inject lateinit var bleConnecting: BleConnecting
    @Inject lateinit var bluetoothAdapter: BluetoothAdapter
    @Inject lateinit var notificationHelper: NotificationHelper

    lateinit var receiveFromUI: ReceiveFromUI
    val sendToUi: SendToUI = SendToUI()
    val bleStates = BleStates()
    val listConnection = ListConnection()

    /**
     * sendValueToUI - то что бубем отправлять в UI по всем устройствам.
     * BleStates - объединяет состояние сканера утройств, соединения с устройством и ошибки.
     * listConnection - контейнер для работы сервиса по всем устройствам, начиная с подключения
     *      и заканчивая получением данных.
     */

    inner class BleServiceBinder: Binder() { fun getService(): BleService = this@BleService }
    override fun onBind(p0: Intent?): IBinder = BleServiceBinder()

    @SuppressLint("ForegroundServiceType")
    fun startBleService() {
        if (bleStates.stateService == StateService.CREATED  ||
            bleStates.stateService == StateService.PAUSED ||
            bleStates.stateService == StateService.STOPPED
        ){
            startForegroundService()
            bleStates.stateService = StateService.STARTED
        }
    }

    private fun startForegroundService() {
        if (!notificationHelper.channelExist()) notificationHelper.createChannel()
        if (Build.VERSION.SDK_INT >= 31)
            startForeground(NOTIFICATION_ID, notificationHelper.build(), FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE)
        else startForeground(NOTIFICATION_ID, notificationHelper.build())
    }

    fun stopBleService(){
        sendToUi.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
        notificationHelper.cancel()
        stopScannerBLEDevices()
        stopScannerBLEDevicesByMac()
        bleStates.stateService = StateService.STOPPED
    }

    fun startScannerBLEDevices(){
        lg("BleService.startScannerBLEDevices valOut.stateScanner.value: ${bleStates.stateScanner}")
        if (bleStates.stateScanner == StateScanner.END){
            bleStates.stateScanner = StateScanner.RUNNING
            bleScanner.startScannerBLEDevices(sendToUi, bleStates)
        }
    }

    private fun stopScannerBLEDevices(){
        if (bleStates.stateScanner == StateScanner.RUNNING){
            bleStates.stateScanner = StateScanner.END
            bleScanner.stopScannerBLEDevices()
            lg("stopScannerBLEDevices")
        }
    }

    fun startScannerBleDeviceByMac(deviceUI: DeviceUI){
        if (bleStates.stateScanner == StateScanner.END){
            bleStates.stateScanner = StateScanner.RUNNING
            receiveFromUI.addressForSearch = deviceUI.address
            lg("startScannerBleDeviceByMac")
            if (deviceUI.address.isNotEmpty()) {
                bleScanner.startScannerBLEDevicesByMac(sendToUi, receiveFromUI, bleStates) }
        }
    }

    private fun stopScannerBLEDevicesByMac(){
        if (bleStates.stateScanner == StateScanner.RUNNING){
            bleStates.stateScanner = StateScanner.END
            lg("stopScannerBLEDevicesByMac")
            bleScanner.stopScannerBLEDevicesByMac(sendToUi)
        }
    }

    @SuppressLint("MissingPermission")
    fun connectDevice(){
        if (bleStates.stateScanner == StateScanner.END)
            bleConnecting.getRemoteDevice { getRemoteDevice(receiveFromUI.addressForSearch) }
        if (bleStates.stateService == StateService.GET_REMOTE_DEVICE)
            bleConnecting.connectingGatt(receiveFromUI, bleStates)
        if (bleStates.stateService == StateService.CONNECT_GAT)
            bleConnecting.startDiscoverService(receiveFromUI, bleStates)
    }

    @SuppressLint("MissingPermission")
    private fun getRemoteDevice(address: String): Boolean {
        var result = false
        bluetoothAdapter.let { adapter ->
            try {
                adapter.getRemoteDevice(address)?.let { dv ->
                    listConnection.addConnection(dv)
                    receiveFromUI.currentConnection = listConnection.addConnection(dv)
                    result = true
                    bleStates.stateService = StateService.GET_REMOTE_DEVICE
                }
            } catch (exception: IllegalArgumentException) {
                lg("Device not found with provided address.")
                bleStates.error = ErrorBleService.GET_REMOTE_DEVICE
            }
        }
        return result
    }

    fun disconnectDevice(){
        bleConnecting.disconnectDevice()
    }

    fun onClearCacheBLE(){
        bleConnecting.clearServicesCache()
    }

    fun readHeartRate() = bleConnecting.readParameterForBle(uuidHeartRate)

//    var bluetoothGatt: BluetoothGatt? = null
//    bluetoothGatt = device.connectGatt(this, false, gattCallback)
//    private val gattUpdateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            when (intent.action) {
//                ACTION_GATT_CONNECTED -> {
//                    valOut.stateService = StateService.CONNECTED
//                    updateConnectionState(R.string.connected)
//                }
//                ACTION_GATT_DISCONNECTED -> {
//                    valOut.stateService = StateService.DISCONNECTED
//                    updateConnectionState(R.string.disconnected)
//                }
//                ACTION_GATT_SERVICES_DISCOVERED -> {
//                    // Show all the supported services and characteristics on the user interface.
//                    displayGattServices(bluetoothService?.getSupportedGattServices())
//                }
//            }
//        }
//    }
//    fun getSupportedGattServices(): List<BluetoothGattService?>? {
//        return bluetoothGatt?.services
//    }
}