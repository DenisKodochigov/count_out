package com.example.count_out.service_app

import android.annotation.SuppressLint
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE
import android.os.Binder
import android.os.Build
import android.os.IBinder
import com.example.count_out.entity.BleTask
import com.example.count_out.entity.ConnectState
import com.example.count_out.entity.Const.NOTIFICATION_ID
import com.example.count_out.entity.ErrorBleService
import com.example.count_out.entity.MessageApp
import com.example.count_out.entity.RunningState
import com.example.count_out.entity.SendToService
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.StateScanner
import com.example.count_out.entity.StateService
import com.example.count_out.entity.bluetooth.BleConnection
import com.example.count_out.entity.bluetooth.BleDevice
import com.example.count_out.entity.bluetooth.BleStates
import com.example.count_out.helpers.NotificationHelper
import com.example.count_out.service.bluetooth.BleConnecting
import com.example.count_out.service.bluetooth.BleScanner
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@AndroidEntryPoint
class BleService @Inject constructor(): Service() {

    @Inject lateinit var bleScanner: BleScanner
    @Inject lateinit var messageApp: MessageApp
    @Inject lateinit var bleConnecting: BleConnecting
    @Inject lateinit var bluetoothAdapter: BluetoothAdapter
    @Inject lateinit var notificationHelper: NotificationHelper

//    lateinit var sendToBle: SendToBle
//    val sendToUi: MutableStateFlow<SendToUI> = MutableStateFlow(SendToUI())
    private val bleStates = BleStates()
    inner class BleServiceBinder: Binder() { fun getService(): BleService = this@BleService }
    override fun onBind(p0: Intent?): IBinder = BleServiceBinder()

    @SuppressLint("ForegroundServiceType")
    fun startService() {
        if (bleStates.stateService == StateService.CREATED  ||
            bleStates.stateService == StateService.PAUSED ||
            bleStates.stateService == StateService.STOPPED
        ){
            startForegroundService()
            bleStates.stateService = StateService.STARTED
        }
    }

    fun stopService(sendToUi: SendToUI){
        sendToUi.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
        notificationHelper.cancel()
        stopScannerBLEDevices(sendToUi)
        bleStates.stateService = StateService.STOPPED
    }

    fun startScanning(){}

    fun stopScanning(){}

    fun receiveHR(){}

    private fun startForegroundService() {
        if (!notificationHelper.channelExist()) notificationHelper.createChannel()
        if (Build.VERSION.SDK_INT >= 31)
            startForeground(NOTIFICATION_ID, notificationHelper.build(), FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE)
        else startForeground(NOTIFICATION_ID, notificationHelper.build())
    }

    fun startScannerBLEDevices(sendToUi: SendToUI){
        if (bleStates.stateScanner == StateScanner.END){
            bleStates.stateScanner = StateScanner.RUNNING_ALL
            bleScanner.startScannerBLEDevicesF(sendToUi, bleStates)
            messageApp.messageApi("Start scanner.")
        }
    }

    fun stopScannerBLEDevices(sendToUi: SendToUI){
        if (bleStates.stateScanner == StateScanner.RUNNING_ALL){
            bleStates.stateScanner = StateScanner.END
            bleScanner.stopScannerBLEDevicesF(sendToUi)
            messageApp.messageApi("Stop scanner.")
        }
    }

    fun connectDevice(sendToUi: SendToUI, sendToBle: SendToService){
        sendToUi.connectingStateF.value = ConnectState.CONNECTING
        bleStates.task = BleTask.CONNECT_DEVICE
        getRemoteDevice(bluetoothAdapter, sendToBle, sendToUi, bleStates)
        sendHeartRate(bleConnecting.heartRate, sendToUi)
        if ( sendToBle.currentConnection != null ) {
            bleConnecting.connectDevice(bleStates, sendToBle)
        }
    }

    private fun sendHeartRate(heartRate: MutableStateFlow<Int>, sendToUi: SendToUI){
        CoroutineScope(Dispatchers.Default).launch {
            heartRate.collect{ hr->
                if(sendToUi.runningState.value == RunningState.Stopped) return@collect
                sendToUi.heartRateF.value = hr
//                sendToUi.update { send-> send.copy( heartRate = hr,) }
                if ( hr > 0) sendToUi.connectingStateF.value = ConnectState.CONNECTED
//                if ( hr > 0) sendToUi.update { send-> send.copy( connectingState = ConnectState.CONNECTED) }
            }
        }
    }
    private fun getRemoteDevice(
        bluetoothAdapter: BluetoothAdapter,
        sendToBle: SendToService,
        sendToUi: SendToUI,
        bleStates: BleStates,
    ): Boolean {
        var result = false
        if (bleStates.stateScanner == StateScanner.END){
            bluetoothAdapter.let { adapter ->
                try {
                    adapter.getRemoteDevice(sendToBle.addressForSearch)?.let { dv ->
                        sendToUi.lastConnectHearthRateDeviceF.value = BleDevice().fromBluetoothDevice(dv)
//                        sendToUi.update { send->
//                            send.copy(lastConnectHearthRateDevice = BleDevice().fromBluetoothDevice(dv)) }
                        sendToBle.currentConnection = BleConnection(device = dv)
                        result = true
                        bleStates.stateService = StateService.GET_REMOTE_DEVICE
                    }
                } catch (exception: IllegalArgumentException) {
                    messageApp.errorApi("Device not found with provided address. $exception")
                    bleStates.error = ErrorBleService.GET_REMOTE_DEVICE
                }
            }
        } else {
            messageApp.messageApi("Running scanner.")
        }
        return result
    }
    fun disconnectDevice(){ bleConnecting.disconnectDevice() }
    fun onClearCacheBLE(){ bleConnecting.clearServicesCache() }
}