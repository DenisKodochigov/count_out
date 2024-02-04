package com.example.count_out

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.count_out.service.InitService
import com.example.count_out.service.WorkoutService
import com.example.count_out.ui.StartApp
import com.example.count_out.ui.joint.NotificationApp
import com.example.count_out.ui.view_components.log
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var notificationApp: NotificationApp
    @Inject lateinit var initService: InitService
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { StartApp() }

    }
    override fun onStart() {
        super.onStart()
        notificationApp.createNotificationChannel()
        log(true, "Begin bind service")
        initService.createService(
            bindService = {
                bindService(Intent( this, WorkoutService::class.java),it, Context.BIND_AUTO_CREATE)}
        )
        log(true, "End bind service")
    }
    override fun onStop() {
        super.onStop()

    }

}
