package com.example.count_out.service.bluetooth

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import com.example.count_out.entity.SendToUI
import com.example.count_out.entity.bluetooth.SendToBle
import com.example.count_out.service.ServiceUtils
import com.example.count_out.service_app.Ble
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
class BleBind @Inject constructor(val context: Context, private val serviceUtils: ServiceUtils
){
    val isBound : MutableStateFlow<Boolean> = MutableStateFlow(false)
    private lateinit var bleService: BleService
    lateinit var service: Ble

    private val bleServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
            bleService = (binder as BleService.BleServiceBinder).getService()
            isBound.value  = true
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            bleService.stopSelf()
            isBound.value = false
        }
    }
    suspend fun bindService(){
        bind(BleService::class.java)
        while (!isBound.value) { delay(100L) }
    }

    private fun <T>bind(clazz: Class<T>) { serviceUtils.bindService(clazz, bleServiceConnection) }

    fun unbindService() { serviceUtils.unbindService(bleServiceConnection, isBound.value) }


//#################################################################################
//    All delete

    fun startBleService(sendToBle: SendToBle): MutableStateFlow<SendToUI> {
        if (isBound.value ) {
            bleService.startBleService()
            bleService.sendToBle = sendToBle
            return bleService.sendToUi
        } else {
            return MutableStateFlow(SendToUI())
        }
    }

    fun stopServiceBle(){ if (isBound.value ) bleService.stopBleService() }

    fun onClearCacheBLE() { bleService.onClearCacheBLE()}

    fun startScannerBLEDevices() {
        if ( isBound.value ) {
            bleService.stopScannerBLEDevices()
            bleService.startScannerBLEDevices()
        }
    }

    fun stopScannerBLEDevices() {if (isBound.value ) bleService.stopScannerBLEDevices()}

    fun connectDevice() {
        if ( isBound.value ) {
            bleService.stopScannerBLEDevices()
            bleService.connectDevice()
        }
    }

    fun disconnectDevice() { if ( isBound.value )   bleService.disconnectDevice()}
}

