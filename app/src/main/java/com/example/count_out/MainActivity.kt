package com.example.count_out

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.count_out.service.bluetooth.BluetoothManager
import com.example.count_out.service.sensors.SensorsApp
import com.example.count_out.service.workout.ServiceManager
import com.example.count_out.service.workout.WorkoutService
import com.example.count_out.ui.StartApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity()
{
    @Inject lateinit var serviceManager: ServiceManager
    @Inject lateinit var sensorsManager: SensorsApp
    @Inject lateinit var bluetoothManager: BluetoothManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { StartApp() }
        sensorsManager.onCreate()
    }

    override fun onStart() {
        super.onStart()
        serviceManager.bindService( WorkoutService::class.java)
    }
    override fun onPause() {
        super.onPause()
        sensorsManager.onPause()
    }
    override fun onStop() {
        super.onStop()
        serviceManager.unbindService()
    }

    override fun onResume() {
        super.onResume()
        sensorsManager.onResume()
    }
}
