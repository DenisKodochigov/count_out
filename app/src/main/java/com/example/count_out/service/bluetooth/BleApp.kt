package com.example.count_out.service.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat
import com.example.count_out.MainActivity
import com.example.count_out.entity.Const.uuidHeartRate
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BleApp @Inject constructor(
    val context: Context,
    private val bluetoothAdapter: BluetoothAdapter,
    private val bleScanner: BleScanner,
    private val bleConnect: BleConnect,
) {

    fun init(activity: MainActivity){
        if ( !bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            ActivityCompat.startActivityForResult(activity, enableBtIntent, 1, null)
        }
    }
    fun startScannerBLEDevices(){
        lg("startScannerBLEDevices ")
        bleScanner.startScannerBLEDevices()
    }
    fun stopScannerBLEDevices(){
        lg("stopScannerBLEDevices")
        bleScanner.stopScannerBLEDevices()
    }
    fun startScannerBleDeviceByMac(mac: String){
        lg("startScannerBleDeviceByMac")
        if (mac.isNotEmpty()) { bleScanner.startScannerBLEDevicesByMac(mac) }
    }
    fun stopScannerBLEDevicesByMac(){
        lg("stopScannerBLEDevicesByMac")
        bleScanner.stopScannerBLEDevicesByMac()
    }
    fun connectDevice(device: BluetoothDevice){
        bleScanner.stopScannerBLEDevices()
        bleConnect.connectDevice(device)
    }

    fun onClearCacheBLE(){
        bleConnect.clearServicesCache()
    }
    fun disconnectDevice(){
        bleConnect.disconnectDevice()
    }
    fun getDevices(): MutableStateFlow<List<BluetoothDevice>> = bleScanner.getDevices()

    fun getDeviceByMac(): MutableStateFlow<List<BluetoothDevice>> = bleScanner.getDeviceByMac()

    fun readHeartRate() = bleConnect.readParameterForBle(uuidHeartRate)
}

