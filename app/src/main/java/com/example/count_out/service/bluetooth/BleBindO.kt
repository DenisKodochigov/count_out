package com.example.count_out.service.bluetooth

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.bluetooth.SendToBle
import com.example.count_out.service.ServiceUtils
import kotlinx.coroutines.delay
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
class BleBindO @Inject constructor(val context: Context, private val serviceUtils: ServiceUtils
){
    val isBound : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private lateinit var bleServiceO: BleServiceO

    private val bleServiceOConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
            bleServiceO = (binder as BleServiceO.BleServiceBinder).getService()
            isBound.value  = true
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            bleServiceO.stopSelf()
            isBound.value = false
        }
    }
    suspend fun bindService(){
        bind(BleServiceO::class.java)
        while (!isBound.value) { delay(100L) }
    }

    private fun <T>bind(clazz: Class<T>) { serviceUtils.bindService(clazz, bleServiceOConnection) }

    fun unbindService() { serviceUtils.unbindService(bleServiceOConnection, isBound.value) }


//#################################################################################
//    All delete

    fun startBleService(sendToBle: SendToBle): MutableStateFlow<SendToUI> {
        if (isBound.value ) {
            bleServiceO.startBleService()
            bleServiceO.sendToBle = sendToBle
            return bleServiceO.sendToUi
        } else {
            return MutableStateFlow(SendToUI())
        }
    }

    fun stopServiceBle(){ if (isBound.value ) bleServiceO.stopBleService() }

    fun onClearCacheBLE() { bleServiceO.onClearCacheBLE()}

    fun startScannerBLEDevices() {
        if ( isBound.value ) {
            bleServiceO.stopScannerBLEDevices()
            bleServiceO.startScannerBLEDevices()
        }
    }

    fun stopScannerBLEDevices() {if (isBound.value ) bleServiceO.stopScannerBLEDevices()}

    fun connectDevice() {
        if ( isBound.value ) {
            bleServiceO.stopScannerBLEDevices()
            bleServiceO.connectDevice()
        }
    }

    fun disconnectDevice() { if ( isBound.value )   bleServiceO.disconnectDevice()}
}

