package com.example.count_out

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import com.example.count_out.permission.RequestPermissionsAll
import com.example.count_out.service.sensors.SensorsApp
import com.example.count_out.service.workout.ServiceManager
import com.example.count_out.ui.StartApp
import com.example.count_out.ui.view_components.lg
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity()
{
    @Inject lateinit var workOutManager: ServiceManager
    @Inject lateinit var sensorsManager: SensorsApp
    @Inject lateinit var bluetoothAdapter: BluetoothAdapter
//    @Inject lateinit var bleManager: BleManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RequestPermissionsAll()
            StartApp()
        }
    }
    override fun onStart() {
        super.onStart()
        ignoreBatteryOptimisation()
//        serviceApp.bindService( DistributionService::class.java)
        if ( !checkBluetoothEnable()) lg(" Bluetooth not enable")
//        workOutManager.bindService( WorkoutService::class.java)
//        sensorsManager.onCreate()
//        if (checkBluetoothEnable()) bleManager.bindBleService(BleService::class.java)
//        else lg(" Bluetooth not enable")
    }

    override fun onStop() {
        super.onStop()
//######
//        serviceApp.unbindService()
//######

//        workOutManager.unbindService()
//        bleManager.unbindService()
    }

    private fun checkBluetoothEnable(): Boolean {
        if ( !bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            ActivityCompat.startActivityForResult(this, enableBtIntent, 1, null)
        }
        return bluetoothAdapter.isEnabled
    }
    @SuppressLint("BatteryLife")
    private fun ignoreBatteryOptimisation(){
        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
        intent.data = Uri.parse("package:" + this.packageName)
        ActivityCompat.startActivityForResult(this, intent, 1, null)
    }
}
