package com.example.count_out.service.bluetooth

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import com.example.count_out.MainActivity
import com.example.count_out.entity.BluetoothDev
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BluetoothApp @Inject constructor(val context: Context, val activity: MainActivity) {
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private val devices: MutableStateFlow<List<BluetoothDev>> = MutableStateFlow(emptyList())

//    private val receiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            val action: String = intent.action.toString()
//            when(action) {
//                BluetoothDevice.ACTION_FOUND -> {
//                    val device = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE, BluetoothDevice::class.java)
//                    } else {
//                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
//                    }
//                }
//            }
//        }
//    }

    private fun <T>checkPermission(granted: T, notGranted: T): T{
        return if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
        ) { notGranted
        } else granted
    }

    fun init(){
//        val bluetoothAvailable = PackageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)
//        val bluetoothLEAvailable = PackageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
        bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
        if ( !bluetoothAdapter.isEnabled) {
            /*
            Next, you need to ensure that Bluetooth is enabled. Call isEnabled() to check whether
            Bluetooth is currently enabled. If this method returns false, then Bluetooth is disabled.
            To request that Bluetooth be enabled, call startActivityForResult(), passing in an
            ACTION_REQUEST_ENABLE intent action. This call issues a request to enable Bluetooth
            through the system settings (without stopping your app).
             */
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult( activity, enableBtIntent, 1, null)
        }
//        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
//        registerReceiver(context, receiver, filter, ContextCompat.RECEIVER_EXPORTED)
    }
    fun onDestroy(){
//        context.unregisterReceiver(receiver)
    }
    fun getDevices() = devices

    suspend fun queryPairedDevices() {
        val job = CoroutineScope(Dispatchers.IO).launch {
            devices.value = checkPermission( bluetoothAdapter.bondedDevices, emptySet()).map {
                BluetoothDev(name = it.name, address = it.address)
            }
            delay(5000L)
        }
        job.cancelAndJoin()
    }
    fun discoverDevices(){

    }
}

