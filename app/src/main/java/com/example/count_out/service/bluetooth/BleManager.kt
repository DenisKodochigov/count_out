package com.example.count_out.service.bluetooth

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import com.example.count_out.entity.StateService
import com.example.count_out.entity.bluetooth.DeviceUI
import com.example.count_out.entity.bluetooth.ReceiveFromUI
import com.example.count_out.entity.bluetooth.SendToUI
import com.example.count_out.service.ServiceUtils
import com.example.count_out.ui.view_components.lg
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
            bleService.bleStates.stateService = StateService.CREATED
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

    fun connectingToServiceBle(receiveFromUI: ReceiveFromUI): SendToUI {
        if (isBound ) {
            bleService.receiveFromUI = receiveFromUI
            return bleService.sendToUi
        } else {
            return SendToUI()
        }
    }

    fun stopServiceBle(){ if (isBound ) bleService.stopBleService() }

    fun onClearCacheBLE() {}

    fun startScannerBLEDevices() {
        if ( isBound ) {
            bleService.stopScannerBLEDevices()
            bleService.stopScannerBLEDevicesByMac()
            bleService.startScannerBLEDevices()
        }
    }

    fun stopScannerBLEDevices() {if (isBound ) bleService.stopScannerBLEDevices()}

    fun startScannerBleDeviceByMac(deviceUI: DeviceUI) {
        lg("BleManager.startScannerBleDeviceByMac isBound: $isBound")
        if ( isBound ) {
            bleService.stopScannerBLEDevices()
            bleService.stopScannerBLEDevicesByMac()
            bleService.startScannerBleDeviceByMac(deviceUI)
        }
    }

    fun stopScannerBLEDevicesByMac(){}

    fun connectDevice() { if ( isBound )  bleService.connectDevice()}

    fun disconnectDevice() { if ( isBound )   bleService.disconnectDevice()}

    fun readHeartRate() = run { if (isBound )   bleService.readHeartRate() }


}

