package com.example.count_out.service_count_out.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.content.Context
import android.os.ParcelUuid
import com.example.count_out.entity.Const
import com.example.count_out.entity.DataForUI
import com.example.count_out.entity.StateBleScanner
import com.example.count_out.entity.bluetooth.BleStates
import com.example.count_out.permission.PermissionApp
import com.example.count_out.service_count_out.stopwatch.TimerMy
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
    fun startScannerBLEDevices(dataForUi: DataForUI, bleStates: BleStates) {
        CoroutineScope(Dispatchers.Default).launch {
            scanCallback = objectScanCallback(bleStates, dataForUi)
            permissionApp.checkBleScan{
                bleScanner.startScan( scanFilters(), scanSettings(0L), scanCallback) }
            dataForUi.scannedBle.value = true
            timer.start(
                sec = timeScanning,
                endCommand = {
                    bleStates.stateBleScanner = StateBleScanner.END
                    stopScanner(dataForUi)
                }
            )
        }
    }
    fun stopScannerBLEDevices(dataForUi: DataForUI) {
        timer.cancel()
        stopScanner(dataForUi)
    }
    @SuppressLint("MissingPermission")
    fun stopScanner(dataForUI: DataForUI){
        lg("BleScanner. Stop scanner")
        permissionApp.checkBleScan{ bleScanner.stopScan(scanCallback)}
        dataForUI.scannedBle.value = false
    }
}
