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
import com.example.count_out.entity.StateScanner
import com.example.count_out.entity.StateService
import com.example.count_out.entity.bluetooth.ValInBleService
import com.example.count_out.entity.bluetooth.ValOutBleService
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

    lateinit var valIn: ValInBleService
    val valOut: ValOutBleService = ValOutBleService()

    inner class BleServiceBinder: Binder() { fun getService(): BleService = this@BleService }
    override fun onBind(p0: Intent?): IBinder = BleServiceBinder()

    @SuppressLint("ForegroundServiceType")
    fun startBleService() {
        if (valOut.stateService == StateService.CREATED  ||
            valOut.stateService == StateService.PAUSED ||
            valOut.stateService == StateService.STOPPED
        ){
            startForegroundService()
            valOut.stateService == StateService.STARTED
        }
    }

    private fun startForegroundService() {
        if (!notificationHelper.channelExist()) notificationHelper.createChannel()
        if (Build.VERSION.SDK_INT >= 31)
            startForeground(NOTIFICATION_ID, notificationHelper.build(), FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE)
        else startForeground(NOTIFICATION_ID, notificationHelper.build())
    }

    fun stopBleService(){
        valOut.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
        notificationHelper.cancel()
        stopScannerBLEDevices()
        stopScannerBLEDevicesByMac()
        valOut.stateService == StateService.STOPPED
    }

    fun startScannerBLEDevices(){
        lg("BleService.startScannerBLEDevices valOut.stateScanner.value: ${valOut.stateScanner.value}")
        if (valOut.stateScanner.value == StateScanner.END){
            valOut.stateScanner.value = StateScanner.RUNNING
            bleScanner.startScannerBLEDevices(valOut)
        }
    }

    private fun stopScannerBLEDevices(){
        if (valOut.stateScanner.value == StateScanner.RUNNING){
            valOut.stateScanner.value = StateScanner.END
            bleScanner.stopScannerBLEDevices()
            lg("stopScannerBLEDevices")
        }
    }

    fun startScannerBleDeviceByMac(mac: String){
        if (valOut.stateScanner.value == StateScanner.END){
            valOut.stateScanner.value = StateScanner.RUNNING
            lg("startScannerBleDeviceByMac")
            if (mac.isNotEmpty()) { bleScanner.startScannerBLEDevicesByMac(mac, valOut) }
        }
    }

    private fun stopScannerBLEDevicesByMac(){
        if (valOut.stateScanner.value == StateScanner.RUNNING){
            valOut.stateScanner.value = StateScanner.END
            lg("stopScannerBLEDevicesByMac")
            bleScanner.stopScannerBLEDevicesByMac(valOut)
        }
    }

    fun connectDevice(){
        if (valOut.stateScanner.value == StateScanner.END) {
            valIn.device.value.device?.let {

                bleConnecting.connectDevice(valIn) }
        }
    }

    fun disconnectDevice(){
        bleConnecting.disconnectDevice()
    }

    fun onClearCacheBLE(){
        bleConnecting.clearServicesCache()
    }

    fun readHeartRate() = bleConnecting.readParameterForBle(uuidHeartRate)
}