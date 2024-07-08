package com.example.count_out.service.bluetooth

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import com.example.count_out.entity.StateService
import com.example.count_out.entity.bluetooth.ValInBleService
import com.example.count_out.entity.bluetooth.ValOutBleService
import com.example.count_out.service.ServiceUtils
import com.example.count_out.ui.view_components.lg
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BleManager @Inject constructor(val context: Context, private val serviceUtils: ServiceUtils
){
    var isBound : Boolean = false
    private lateinit var bleService: BleService

    private val bleServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
            val binderService = binder as BleService.BleServiceBinder
            bleService = binderService.getService()
            bleService.valOut.stateService = StateService.CREATED
            bleService.startBleService()
            isBound  = true
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            bleService.stopSelf()
            isBound  = false
        }
    }

    fun <T>bindBleService(clazz: Class<T>) { serviceUtils.bindService(clazz, bleServiceConnection) }

    fun unbindService() { serviceUtils.unbindService(bleServiceConnection, isBound) }

    fun connectingToServiceBle(valIn: ValInBleService): ValOutBleService {
        if (isBound ) {
            bleService.valIn = valIn
            return bleService.valOut
        } else {
            return ValOutBleService()
        }
    }

    fun stopServiceBle(){ if (isBound ) bleService.stopBleService() }

    fun onClearCacheBLE() {}

    fun startScannerBLEDevices() {
        if ( isBound ) bleService.startScannerBLEDevices()
    }

    fun stopScannerBLEDevices() {}

    fun startScannerBleDeviceByMac(mac: String) {
        lg("BleManager.startScannerBleDeviceByMac isBound: $isBound")
        if ( isBound ) bleService.startScannerBleDeviceByMac(mac)
    }

    fun stopScannerBLEDevicesByMac(){}

    fun connectDevice() { if (isBound )  bleService.connectDevice()}

    fun disconnectDevice() { if ( isBound )   bleService.disconnectDevice()}

    fun readHeartRate() = run { if (isBound )   bleService.readHeartRate() }

}

