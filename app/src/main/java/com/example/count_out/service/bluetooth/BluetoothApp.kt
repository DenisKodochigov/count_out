package com.example.count_out.service.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat
import com.example.count_out.MainActivity
import com.example.count_out.entity.BluetoothDeviceApp
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BluetoothApp @Inject constructor(
    val context: Context,
    private val bluetoothAdapter: BluetoothAdapter,
    private val bluetoothScanner: BluetoothScanner,
    private val bluetoothConnect: BluetoothConnect,
) {

    fun init(activity: MainActivity){
        if ( !bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            ActivityCompat.startActivityForResult(activity, enableBtIntent, 1, null)
        }
    }
    fun startScannerBLEDevices(){
        bluetoothScanner.startScannerBLEDevices()
    }
    fun stopScannerBLEDevices(){
        bluetoothScanner.stopScannerBLEDevices()
    }
    fun stopScannerBLEDevicesByMac(){
        bluetoothScanner.stopScannerBLEDevicesByMac()
    }
    fun connectDevice(device: BluetoothDevice){
        bluetoothScanner.stopScannerBLEDevices()
        bluetoothConnect.connectDevice(device)
    }
    fun findBleDeviceByMac(mac: String){
        bluetoothScanner.findBleDeviceByMac(mac)
    }
    fun disconnectDevice(){
        bluetoothConnect.disconnectDevice()
    }
    fun getDevices(): MutableStateFlow<List<BluetoothDevice>> = bluetoothScanner.getDevices()
    fun getDeviceOnMac(): MutableSharedFlow<BluetoothDeviceApp> = bluetoothScanner.getDeviceBYMac()
}

