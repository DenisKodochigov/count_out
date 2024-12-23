package com.example.count_out.data.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.content.Context
import android.os.ParcelUuid
import com.example.count_out.data.bluetooth.modules.BleStates
import com.example.count_out.entity.Const
import com.example.count_out.entity.StateBleScanner
import com.example.count_out.domain.router.DataFromBle
import com.example.count_out.ui.permission.PermissionApp
import com.example.count_out.services.timer.TimerMy
import com.example.count_out.ui.view_components.lg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BleScanner @Inject constructor(
    val context: Context,
    val bluetoothAdapter: BluetoothAdapter,
    private val permissionApp: PermissionApp
) {
    private val timer = TimerMy()
    private lateinit var scanCallback: ScanCallback
    private val bleScanner by lazy { bluetoothAdapter.bluetoothLeScanner }
    private val timeScanning = 120

    /** Scan all device*/
    private fun scanFilters(): List<ScanFilter> {
        val filters = mutableListOf<ScanFilter>()
        for(serviceUUID in Const.serviceUUIDs){
            val filter = ScanFilter.Builder().setServiceUuid(
                ParcelUuid(serviceUUID),
                ParcelUuid(UUID.fromString("11111111-0000-0000-0000-000000000000"))
            ).build()
            filters.add(filter)
        }
        return filters
    }

    @SuppressLint("MissingPermission")
    fun startScannerBLEDevices(dataFromBle: DataFromBle, bleStates: BleStates) {
        CoroutineScope(Dispatchers.Default).launch {
            scanCallback = objectScanCallback(bleStates, dataFromBle)
            permissionApp.checkBleScan{
                bleScanner.startScan( scanFilters(), scanSettings(0L), scanCallback) }
            dataFromBle.scannedBle.value = true
            timer.start(
                sec = timeScanning,
                endCommand = {
                    bleStates.stateBleScanner = StateBleScanner.END
                    stopScanner(dataFromBle)
                }
            )
        }
    }
    fun stopScannerBLEDevices(dataFromBle: DataFromBle) {
        timer.cancel()
        stopScanner(dataFromBle)
    }
    @SuppressLint("MissingPermission")
    fun stopScanner(dataFromBle: DataFromBle){
        lg("BleScanner. Stop scanner")
        permissionApp.checkBleScan{ bleScanner.stopScan(scanCallback)}
        dataFromBle.scannedBle.value = false
    }
}
