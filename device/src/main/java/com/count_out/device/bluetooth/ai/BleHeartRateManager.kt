package com.count_out.device.bluetooth.ai

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.ParcelUuid
import android.util.Log
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.pow
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.math.min

@SuppressLint("MissingPermission")
class BleHeartRateManager @Inject constructor(private val context: Context) : HeartRateProvider {
    companion object {
        private const val TAG = "BleHRManager"
        private const val DEFAULT_SCAN_TIMEOUT = 10_000L
        private const val MAX_RECONNECT_ATTEMPTS = 6
    }

    // Flows exposed
    private val _heartRateFlow = MutableStateFlow<Int?>(null)
    override val heartRateFlow = _heartRateFlow.asStateFlow()

    private val _rrFlow = MutableSharedFlow<List<Int>>(replay = 1)
    override val rrIntervalFlow = _rrFlow.asSharedFlow()

    private val _connState = MutableStateFlow(BleConnectionState.DISCONNECTED)
    override val connectionStateFlow = _connState.asStateFlow()

    // Bluetooth objects
    private val bluetoothManager: BluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
    private val scanner: BluetoothLeScanner? = bluetoothAdapter?.bluetoothLeScanner

    // GATT
    private var bluetoothGatt: BluetoothGatt? = null
    private var connectedDeviceAddress: String? = null

    // coroutine scope
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // reconnect control
    private val reconnectAttempts = AtomicInteger(0)
    private var manualDisconnect = false

    // Scan callback (instantiated lazily)
    private val scanCallback: ScanCallback by lazy {
        object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                result?.device?.let { device ->
                    Log.d(TAG, "Scan found device ${device.address}, name=${device.name}")
                    // Stop scanning and connect
                    stopScan()
                    scope.launch { connectTo(device.address) }
                }
            }

            override fun onScanFailed(errorCode: Int) {
                Log.w(TAG, "Scan failed: $errorCode")
                _connState.value = BleConnectionState.DISCONNECTED
            }
        }
    }

    // GATT callback
    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            Log.d(TAG, "GATT onConnectionStateChange status=$status newState=$newState")
            if (status != BluetoothGatt.GATT_SUCCESS) {
                // treat as disconnect
                Log.w(TAG, "GATT status not success: $status")
                closeGatt()
                scheduleReconnect()
                return
            }

            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    connectedDeviceAddress = gatt.device.address
                    reconnectAttempts.set(0)
                    _connState.value = BleConnectionState.CONNECTED
                    // Discover services
                    Handler(Looper.getMainLooper()).post { gatt.discoverServices() }
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    Log.d(TAG, "GATT disconnected")
                    _connState.value = BleConnectionState.DISCONNECTED
                    closeGatt()
                    scheduleReconnect()
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            Log.d(TAG, "Services discovered: status=$status")
            if (status != BluetoothGatt.GATT_SUCCESS) {
                Log.w(TAG, "Service discovery failed")
                return
            }
            val hrService = gatt.getService(BleConstants.HR_SERVICE_UUID)
            val hrChar = hrService?.getCharacteristic(BleConstants.HR_MEASUREMENT_CHAR_UUID)
            if (hrChar == null) {
                Log.w(TAG, "HR measurement char not found")
                return
            }
            // enable notification
            val enabled = gatt.setCharacteristicNotification(hrChar, true)
            Log.d(TAG, "setCharacteristicNotification: $enabled")
            val ccc = hrChar.getDescriptor(BleConstants.CLIENT_CHAR_CONFIG_UUID)
            ccc?.let { descriptor ->
                descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                gatt.writeDescriptor(descriptor)
            }
        }

        override fun onDescriptorWrite(gatt: BluetoothGatt?, descriptor: BluetoothGattDescriptor?, status: Int) {
            Log.d(TAG, "Descriptor write: ${descriptor?.uuid} status=$status")
            // no-op
        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?) {
            characteristic?.let { char ->
                try {
                    if (char.uuid == BleConstants.HR_MEASUREMENT_CHAR_UUID) {
                        val bytes = char.value ?: return
                        val parsed = parseHeartRateMeasurement(bytes)
                        _heartRateFlow.value = parsed.heartRate
                        if (parsed.rrIntervals.isNotEmpty()) {
                            scope.launch { _rrFlow.emit(parsed.rrIntervals) }
                        }
                    }
                } catch (t: Throwable) {
                    Log.e(TAG, "Error parsing HR char", t)
                }
            }
        }
    }

    // region scanning/connect APIs

    /**
     * Start scanning for devices advertising Heart Rate Service and connect to the first found.
     * Requires BLUETOOTH_SCAN (and on Android < 12 location perms).
     */
    override suspend fun startScanAndConnect(timeoutMs: Long) {
        if (bluetoothAdapter == null || scanner == null) {
            Log.e(TAG, "Bluetooth not supported or adapter null")
            return
        }
        manualDisconnect = false
        _connState.value = BleConnectionState.SCANNING

        // Build scan filter for HR service
        val filter = ScanFilter.Builder()
            .setServiceUuid(ParcelUuid(BleConstants.HR_SERVICE_UUID))
            .build()
        val settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .build()

        scanner.startScan(listOf(filter), settings, scanCallback)

        // timeout
        withTimeoutOrNull(timeoutMs) {
            // suspend until connected or timed out
            while (_connState.value == BleConnectionState.SCANNING) {
                delay(200)
            }
        } ?: run {
            // timed out
            Log.d(TAG, "Scan timed out")
            stopScan()
            _connState.value = BleConnectionState.DISCONNECTED
        }
    }

    fun stopScan() {
        try {
            scanner?.stopScan(scanCallback)
        } catch (t: Throwable) {
            Log.w(TAG, "stopScan failed", t)
        }
    }

    /**
     * Connect to specific device address.
     */
    override suspend fun connectTo(address: String) {
        if (bluetoothAdapter == null) {
            Log.e(TAG, "BluetoothAdapter is null")
            return
        }
        manualDisconnect = false
        _connState.value = BleConnectionState.CONNECTING

        val device = bluetoothAdapter.getRemoteDevice(address)
        if (device == null) {
            Log.e(TAG, "Remote device null for $address")
            _connState.value = BleConnectionState.DISCONNECTED
            return
        }

        // Ensure previous gatt closed
        closeGatt()

        // connectGatt must be called on main looper on some OEMs
        withContext(Dispatchers.Main) {
            bluetoothGatt = device.connectGatt(context, false, gattCallback, BluetoothDevice.TRANSPORT_LE)
        }
    }

    override suspend fun disconnect() {
        manualDisconnect = true
        _connState.value = BleConnectionState.DISCONNECTED
        closeGatt()
    }

    override fun isConnected(): Boolean = _connState.value == BleConnectionState.CONNECTED

    private fun closeGatt() {
        try {
            bluetoothGatt?.apply {
                disconnect()
                close()
            }
        } catch (t: Throwable) {
            Log.w(TAG, "closeGatt error", t)
        } finally {
            bluetoothGatt = null
            connectedDeviceAddress = null
        }
    }
    // endregion

    // region reconnect logic
    private fun scheduleReconnect() {
        if (manualDisconnect) {
            Log.d(TAG, "manual disconnect -> no reconnect")
            return
        }
        val attempts = reconnectAttempts.incrementAndGet()
        if (attempts > MAX_RECONNECT_ATTEMPTS) {
            Log.w(TAG, "Max reconnect attempts reached")
            return
        }
        val delayMs = calculateBackoffDelay(attempts)
        Log.d(TAG, "schedule reconnect in ${delayMs}ms (attempts=$attempts)")
        scope.launch {
            delay(delayMs)
            val addr = connectedDeviceAddress
            if (addr != null) {
                Log.d(TAG, "reconnecting to $addr")
                connectTo(addr)
            } else {
                // If we lost device address, start scanning
                startScanAndConnect(DEFAULT_SCAN_TIMEOUT)
            }
        }
    }

    private fun calculateBackoffDelay(attempts: Int): Long {
        // exponential backoff with cap (e.g., 2^attempts * 500ms, capped at 30s)
        val base = 500.0
        val delay = base * 2.0.pow((attempts - 1).toDouble())
        return min(delay.toLong(), 30_000L)
    }
    // endregion

    // region parsing HRM characteristic
    private data class ParsedHR(val heartRate: Int?, val rrIntervals: List<Int>)

    private fun parseHeartRateMeasurement(value: ByteArray): ParsedHR {
        if (value.isEmpty()) return ParsedHR(null, emptyList())
        val flags = value[0].toInt() and 0xFF
        val hrFormatUint16 = flags and 0x01 != 0
        val sensorContact = (flags shr 1) and 0x03
        val energyExpendedPresent = flags and 0x08 != 0
        val rrIntervalPresent = flags and 0x10 != 0

        var offset = 1
        val heartRate = if (!hrFormatUint16) {
            if (offset >= value.size) null else (value[offset++].toInt() and 0xFF)
        } else {
            if (offset + 1 >= value.size) null
            else {
                val hr = ((value[offset].toInt() and 0xFF) or ((value[offset + 1].toInt() and 0xFF) shl 8))
                offset += 2
                hr
            }
        }

        if (energyExpendedPresent) {
            offset += 2 // skip energy expended field
        }

        val rrList = mutableListOf<Int>()
        if (rrIntervalPresent) {
            while (offset + 1 < value.size) {
                // RR interval is uint16 in units of 1/1024 second. Convert to ms: rr_ms = (raw / 1024) * 1000
                val raw = ((value[offset].toInt() and 0xFF) or ((value[offset + 1].toInt() and 0xFF) shl 8))
                val rrMs = ((raw.toDouble() / 1024.0) * 1000.0).toInt()
                rrList.add(rrMs)
                offset += 2
            }
        }

        return ParsedHR(heartRate, rrList)
    }
    // endregion

    // cleanup
    fun shutdown() {
        manualDisconnect = true
        stopScan()
        closeGatt()
        scope.cancel()
    }
}