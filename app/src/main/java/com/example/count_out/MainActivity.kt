package com.example.count_out

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.count_out.permission.PermissionApp
import com.example.count_out.service.bluetooth.BleManager
import com.example.count_out.service.sensors.SensorsApp
import com.example.count_out.service.workout.ServiceManager
import com.example.count_out.ui.StartApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity()
{
    @Inject lateinit var workOutManager: ServiceManager
    @Inject lateinit var permissionApp: PermissionApp
    @Inject lateinit var sensorsManager: SensorsApp
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
//        workOutManager.bindService( WorkoutService::class.java)
//        sensorsManager.onCreate()
        bleManager.startServiceBle( this )
//        bleManager.checkBluetoothEnable(this)
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
}
