package com.example.count_out

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.count_out.service.ServiceManager
import com.example.count_out.service.WorkoutService
import com.example.count_out.ui.StartApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity()
{

    @Inject lateinit var serviceManager: ServiceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { StartApp() }
    }

    override fun onStart() {
        super.onStart()
        serviceManager.bindService( WorkoutService::class.java)
    }
}
