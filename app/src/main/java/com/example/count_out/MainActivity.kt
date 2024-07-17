package com.example.count_out

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import com.example.count_out.permission.PermissionApp
import com.example.count_out.service.bluetooth.BleManager
import com.example.count_out.service.bluetooth.BleService
import com.example.count_out.service.sensors.SensorsApp
import com.example.count_out.service.workout.ServiceManager
import com.example.count_out.service.workout.WorkoutService
import com.example.count_out.ui.StartApp
import com.example.count_out.ui.view_components.lg
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity()
{
    @Inject lateinit var workOutManager: ServiceManager
    @Inject lateinit var permissionApp: PermissionApp
    @Inject lateinit var sensorsManager: SensorsApp
    @Inject lateinit var bluetoothAdapter: BluetoothAdapter
    @Inject lateinit var bleManager: BleManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            permissionApp.RequestPermissions()
            StartApp()
        }
    }

    override fun onStart() {
        super.onStart()
        lg("onStart ${Thread.currentThread().name}")
        workOutManager.bindService( WorkoutService::class.java)
//        sensorsManager.onCreate()
        if (checkBluetoothEnable()) bleManager.bindBleService(BleService::class.java)
        else lg(" Bluetooth not enable")
    }
    override fun onPause() {
        super.onPause()
//        sensorsManager.onPause()
    }
    override fun onStop() {
        super.onStop()
        workOutManager.unbindService()
        bleManager.unbindService()
    }

    override fun onResume() {
        super.onResume()
//        sensorsManager.onResume()
    }
    private fun checkBluetoothEnable(): Boolean
    {
        if ( !bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            ActivityCompat.startActivityForResult(this, enableBtIntent, 1, null)
        }
        return bluetoothAdapter.isEnabled
    }
}
