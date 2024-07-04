package com.example.count_out.service.bluetooth

import android.annotation.SuppressLint
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.ActivityCompat
import com.example.count_out.MainActivity
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
class BleService @Inject constructor(
    private val bluetoothAdapter: BluetoothAdapter,
    private val bleScanner: BleScanner,
    private val bleConnecting: BleConnecting,
): Service() {

    var valIn: ValInBleService = ValInBleService()
    var valOut: ValOutBleService = ValOutBleService()
    @Inject lateinit var notificationHelper: NotificationHelper

    inner class BleServiceBinder: Binder() { fun getService(): BleService = this@BleService }
    override fun onBind(p0: Intent?): IBinder = BleServiceBinder()

    private fun startForegroundService()
    {
        if (!notificationHelper.channelExist()) notificationHelper.createChannel()
        if (Build.VERSION.SDK_INT >= 31)
            startForeground(NOTIFICATION_ID, notificationHelper.build(), FOREGROUND_SERVICE_TYPE_SPECIAL_USE)
        else startForeground(NOTIFICATION_ID, notificationHelper.build())
    }
    fun checkBluetoothEnable(activity: MainActivity): Boolean
    {
        if ( !bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            ActivityCompat.startActivityForResult(activity, enableBtIntent, 1, null)
        }
        return bluetoothAdapter.isEnabled
    }
    @SuppressLint("ForegroundServiceType")
    fun startBleService() {
        valOut.stateRunning.value.let {
            if (it == StateRunning.Stopped || it == StateRunning.Created){
                valOut.stateRunning.value = StateRunning.Started
                startForegroundService()
            } else if (it == StateRunning.Paused) {  }
        }
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