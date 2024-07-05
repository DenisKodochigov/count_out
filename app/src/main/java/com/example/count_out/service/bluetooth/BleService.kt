package com.example.count_out.service.bluetooth

import android.annotation.SuppressLint
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
import android.os.Binder
import android.os.Build
import android.os.IBinder
import com.example.count_out.entity.Const.NOTIFICATION_ID
import com.example.count_out.entity.Const.uuidHeartRate
import com.example.count_out.entity.StateRunning
import com.example.count_out.entity.bluetooth.ValInBleService
import com.example.count_out.entity.bluetooth.ValOutBleService
import com.example.count_out.helpers.NotificationHelper
import com.example.count_out.ui.view_components.lg
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@AndroidEntryPoint
class BleService @Inject constructor(): Service() {

    @Inject lateinit var bleScanner: BleScanner
    @Inject lateinit var bleConnecting: BleConnecting
    @Inject lateinit var bluetoothAdapter: BluetoothAdapter
    @Inject lateinit var notificationHelper: NotificationHelper

    var valIn: ValInBleService = ValInBleService()
    var valOut: ValOutBleService = ValOutBleService()

    inner class BleServiceBinder: Binder() { fun getService(): BleService = this@BleService }
    override fun onBind(p0: Intent?): IBinder = BleServiceBinder()

    @SuppressLint("ForegroundServiceType")
    fun startBleService() {
        valOut.stateRunning.value.let {
            if (it == StateRunning.Stopped || it == StateRunning.Created){
                valOut.stateRunning.value = StateRunning.Started
                startForegroundService()
            } else if (it == StateRunning.Paused) {  }
        }
    }

    private fun startForegroundService()
    {
        if (!notificationHelper.channelExist()) notificationHelper.createChannel()
        if (Build.VERSION.SDK_INT >= 31)
            startForeground(NOTIFICATION_ID, notificationHelper.build(), FOREGROUND_SERVICE_TYPE_SPECIAL_USE)
        else startForeground(NOTIFICATION_ID, notificationHelper.build())
    }

    fun stopBleService(){
        valOut.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
        notificationHelper.cancel()
        stopScannerBLEDevices()
        stopScannerBLEDevicesByMac()
    }

    fun startScannerBLEDevices(){
        lg("startScannerBLEDevices ")
        valOut.listDevice = bleScanner.scannerBleAll.devices
        bleScanner.startScannerBLEDevices()
//        lgF(valOut.listDevice, "BleService.startScannerBLEDevices valOut.listDevice:")
    }
    fun writeListTest(){
        valOut.list = bleScanner.list
        bleScanner.writeListTest()
//        val list: MutableList<Int> = mutableListOf()
//        valOut.list = flow{
//
//            repeat(100) { value ->
//                delay(1000)
//                list.add(value)
//                emit(list)
//            }
//        }
//        CoroutineScope(Dispatchers.Default).launch {
//            val list: MutableList<Int> = mutableListOf()
//            for (i in 0..1000){
//                delay(1000L)
//                list.add(i)
//                valOut.list = list
//                lg(" Add into list test i: $i, valOut.list.size: ${valOut.list.value.size}")
//            }
//        }
    }
    private fun stopScannerBLEDevices(){
        lg("stopScannerBLEDevices")
        bleScanner.stopScannerBLEDevices()
        valOut.listDevice = MutableStateFlow(emptyList())
    }
    fun startScannerBleDeviceByMac(mac: String){
        lg("startScannerBleDeviceByMac")
        if (mac.isNotEmpty()) {
            valOut.listDevice = bleScanner.scannerBleByMac.devices
            bleScanner.startScannerBLEDevicesByMac(mac) }
    }
    private fun stopScannerBLEDevicesByMac(){
        lg("stopScannerBLEDevicesByMac")
        bleScanner.stopScannerBLEDevicesByMac()
        valOut.listDevice = MutableStateFlow(emptyList())
    }

    fun connectDevice(){
        bleScanner.stopScannerBLEDevices()
        valIn.device.value.device?.let { bleConnecting.connectDevice(valIn) }
    }

    fun disconnectDevice(){
        bleConnecting.disconnectDevice()
    }

    fun onClearCacheBLE(){
        bleConnecting.clearServicesCache()
    }

    fun readHeartRate() = bleConnecting.readParameterForBle(uuidHeartRate)

}