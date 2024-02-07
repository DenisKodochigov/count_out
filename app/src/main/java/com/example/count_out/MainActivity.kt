package com.example.count_out

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.count_out.service.ServiceManager
import com.example.count_out.service.WorkoutService
import com.example.count_out.ui.StartApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity()
{
    @Inject lateinit var serviceManager: ServiceManager
    @RequiresApi(Build.VERSION_CODES.S)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { StartApp() }
    }
    override fun onStart() {
        super.onStart()
        serviceManager.bindService(this@MainActivity, WorkoutService::class.java)

    }
    override fun onStop() {
        super.onStop()
        if (serviceManager.isBound) serviceManager.unbindService(this@MainActivity)
    }
}
