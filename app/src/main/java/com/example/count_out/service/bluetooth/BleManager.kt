package com.example.count_out.service.bluetooth

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.example.count_out.MainActivity
import com.example.count_out.entity.bluetooth.ValInBleService
import com.example.count_out.entity.bluetooth.ValOutBleService
import com.example.count_out.ui.view_components.lg
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BleManager @Inject constructor( val context: Context
) {
    var isBound : Boolean = false
    private lateinit var bleService: BleService

    private val bleServiceConnection = object : ServiceConnection
    {
        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
            lg(" onServiceConnected isBound: $isBound")
            val binderService = binder as BleService.BleServiceBinder

            bleService = binderService.getService()
            bleService.startBleService()
            isBound  = true
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            bleService.stopSelf()
            isBound  = false
        }
    }

    fun checkBluetoothEnable(activity: MainActivity) = bleService.checkBluetoothEnable(activity)

    fun startServiceBle(activity: MainActivity){
        lg(" startServiceBle isBound: $isBound")
//        bindService(BleService::class.java)
        context.bindService( Intent(context, BleService::class.java), bleServiceConnection, Context.BIND_AUTO_CREATE)
//        bleService.checkBluetoothEnable(activity)
    }

    private fun <T>bindService(clazz: Class<T>) {
        context.bindService( Intent(context, clazz), bleServiceConnection, Context.BIND_AUTO_CREATE)
    }
    fun unbindService() { if (isBound) context.unbindService(bleServiceConnection) }
    fun connectingToServiceBle(valIn: ValInBleService): ValOutBleService {
        return if (isBound ) {
            bleService.valIn = valIn
            bleService.valOut
        } else { ValOutBleService()}
    }
    fun stopServiceBle(){
        if (isBound ) bleService.stopBleService()
    }
    fun onClearCacheBLE() {}
    fun startScannerBLEDevices() = bleService.startScannerBLEDevices()
    fun stopScannerBLEDevices() {}
    fun startScannerBleDeviceByMac(mac: String) = bleService.startScannerBleDeviceByMac(mac)
    fun stopScannerBLEDevicesByMac(){}
    fun connectDevice() = bleService.connectDevice()
    fun disconnectDevice() = bleService.disconnectDevice()
    fun readHeartRate() = bleService.readHeartRate()

}

