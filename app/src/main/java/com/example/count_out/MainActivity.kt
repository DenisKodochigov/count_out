package com.example.count_out

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.count_out.service.WorkoutService
import com.example.count_out.service.WorkoutService.Companion.mBound
import com.example.count_out.ui.StartApp
import com.example.count_out.ui.joint.NotificationApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var notificationApp: NotificationApp
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { StartApp() }
    }
    override fun onStart() {
        super.onStart()
        notificationApp.createNotificationChannel()
        // Bind to LocalService.

    }
    override fun onStop() {
        super.onStop()

    }

}
