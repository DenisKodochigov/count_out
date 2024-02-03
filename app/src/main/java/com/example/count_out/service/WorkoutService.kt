package com.example.count_out.service

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.icu.text.SimpleDateFormat
import android.os.Binder
import android.os.IBinder
import android.widget.Toast
import com.example.count_out.ui.view_components.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.Date
import java.util.Locale

class WorkoutService: Service() {

    private var pauseService: Boolean = false
    private val serviceBinder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): WorkoutService = this@WorkoutService
    }
    override fun onBind(p0: Intent?): IBinder {
        return serviceBinder
    }
    override fun onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
    }
    fun startService(context: Context){
        Intent( context, WorkoutService::class.java).also { intent ->
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }
    fun stopService(){
        unbindService(serviceConnection)
        mBound = false
    }

    fun startWorkout(){
        while(!pauseService){
            runBlocking { delay(2000) }
            log(true, "${getCurrentTime()} Text function into service")
        }
    }
    fun pauseWorkout(){
        pauseService = !pauseService
    }
    fun stopWorkout(){
        mBound = false
    }
    private fun getCurrentTime(): String {
        return SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.US).format(Date())
    }
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
            val binderWorkoutService = binder as WorkoutService.LocalBinder
            mService = binderWorkoutService.getService()
            mBound = true
        }
        override fun onServiceDisconnected(arg0: ComponentName) { mBound = false }
    }

    companion object {
        lateinit var mService: WorkoutService
        var mBound: Boolean = false
    }
}