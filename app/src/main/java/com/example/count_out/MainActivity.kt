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
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    @Inject lateinit var initService: InitService

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { StartApp() }

    }
    override fun onStart() {
        super.onStart()
        initService.bind = {  bindService(
            Intent( this, WorkoutService::class.java),it, Context.BIND_AUTO_CREATE)}
        initService.unbind = {  unbindService(it) }
    }
    override fun onStop() {
        super.onStop()
    }
}
