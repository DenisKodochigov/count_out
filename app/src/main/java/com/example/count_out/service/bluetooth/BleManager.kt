package com.example.count_out.service.bluetooth

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.bluetooth.SendToBle
import com.example.count_out.service.ServiceUtils
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ######################Connect to device #####################################################
1   initBleService(){}:  bleServiceConnection.onServiceConnected.BleService.startBleService()
2   checkBleAdapter(){}:
3   connectByAddress( address: String){ bleService.connectByAddress( address )}
4   use callBack BluetoothGattCallback.onConnectionStateChange in BleConnecting

https://developer.android.com/develop/connectivity/bluetooth/ble/transfer-ble-data#kotlin
 */
@Singleton
class BleManager @Inject constructor(val context: Context, private val serviceUtils: ServiceUtils
){
    var isBound : Boolean = false
    private lateinit var bleService: BleService

    private val bleServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
            bleService = (binder as BleService.BleServiceBinder).getService()
            isBound  = true
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            bleService.stopSelf()
            isBound  = false
        }
    }

    fun <T>bindBleService(clazz: Class<T>) { serviceUtils.bindService(clazz, bleServiceConnection) }

    fun unbindService() { serviceUtils.unbindService(bleServiceConnection, isBound) }

    fun startBleService(sendToBle: SendToBle): MutableStateFlow<SendToUI> {
        if (isBound ) {
            bleService.startBleService()
            bleService.sendToBle = sendToBle
            lg( "BleManager ${bleService.sendToUi}: ")
            return bleService.sendToUi
        } else {
            return MutableStateFlow(SendToUI())
        }
    }

    fun stopServiceBle(){ if (isBound ) bleService.stopBleService() }

    fun onClearCacheBLE() { bleService.onClearCacheBLE()}

    fun startScannerBLEDevices() {
        if ( isBound ) {
            bleService.stopScannerBLEDevices()
            bleService.startScannerBLEDevices()
        }
    }

    fun stopScannerBLEDevices() {if (isBound ) bleService.stopScannerBLEDevices()}

    fun connectDevice() {
        if ( isBound ) {
            bleService.stopScannerBLEDevices()
            bleService.connectDevice()
        }
    }

    fun disconnectDevice() { if ( isBound )   bleService.disconnectDevice()}
}

