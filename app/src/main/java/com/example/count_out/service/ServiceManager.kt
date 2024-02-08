package com.example.count_out.service

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.example.count_out.entity.Training
import com.example.count_out.ui.view_components.log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceManager @Inject constructor( val context: Context
){
    var mService: WorkoutService? = null
    var isBound : Boolean = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, binder: IBinder) {
            val binderService = binder as WorkoutService.WorkoutServiceBinder
            mService = binderService.getService()
            isBound  = true
            log(true, "onServiceConnected. isBound = $isBound, mService = $mService")
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            mService = null
            isBound  = false
            log(true, "onServiceDisconnected. isBound = $isBound")}
    }
    fun startWorkout(training: Training){
        if (isBound ) {
            mService?.startWorkout(training)
            log(true, "startWorkout. mService?.startWorkout(training). isBound = $isBound, mService = $mService")
        }
    }
    fun pauseWorkout(){
        log(true, "Pause workout service. isBound = $isBound")
        if (isBound ) mService?.pauseWorkout()
    }
    fun stopWorkout(){
        unbindService()
            log(true, "stopWorkout. unbindService. mService = $mService")
        mService?.stopWorkout()
            log(true, "stopWorkout. mService?.stopWorkout(). mService = $mService")
        mService?.stopSelf()
            log(true, "stopWorkout. mService?.stopSelf(). mService = $mService")
    }
    fun <T>bindService( clazz: Class<T>) {
        context.bindService(Intent(context, clazz), serviceConnection, BIND_AUTO_CREATE)
        log(true, "bindService: $serviceConnection")
    }
    fun unbindService() {
        if (isBound) context.unbindService(serviceConnection)
        log(true, "unbindService: $serviceConnection")
    }
}

